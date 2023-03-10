#!/bin/bash

# constants

JRE_PATH_IN_APP='/Contents/PlugIns/jre'
ENTITLEMENTS_JVM='../default-entitlements-jvm.plist'
ENTITLEMENTS_LAUNCHER='../default-entitlements-launcher.plist'
CODESIGN_ARGS=("-v" "--timestamp" "--force" "--options" "runtime")

# variables

DEBUG=true

# input

INPUT=""
CODESIGN_IDENTITY=""

# functions

usage() {
    echo "Usage: $0 -s [<codesign-identity>] -p [<input-path-to-sign>]" 1>&2; exit 1;
}

exit_abnormal() {
  usage
  exit 1
}

codesign_jre() {
    # codesign libs files
    echo "Code-signing JRE libs in ${1}$JRE_PATH_IN_APP"
    find "${1}$JRE_PATH_IN_APP" -depth -type f -exec codesign -s "$CODESIGN_IDENTITY" "${CODESIGN_ARGS[@]}" {} \;
     # find and deep sign .jar files inside the JRE
#    deepsign_jar "${1}$JRE_PATH_IN_APP"
    # Sign the Java executable with entitlements
    echo "Code-signing JRE executables"
    codesign -s "$CODESIGN_IDENTITY" "${CODESIGN_ARGS[@]}" --entitlements ${ENTITLEMENTS_JVM} ${1}$JRE_PATH_IN_APP/Contents/Home/bin/*
}

# extracts .jar contents, find *lib files and signes them
deepsign_jar(){
    echo "Deep signing jar files in " "${1}"
    # deep codesign jar files
    for JAR_PATH in `find "${1}" -depth -name "*.jar"`; do
        if [ $DEBUG ]; then
                echo "Now $JAR_PATH"
        fi
        JAR_FILENAME=$(basename "${JAR_PATH}")
        if [ $DEBUG ]; then
            echo "Working on $JAR_FILENAME"
        fi
          OUTPUT_PATH=${JAR_PATH%.*}
        if [ $DEBUG ]; then
            echo "Output path $OUTPUT_PATH"
        fi
        unzip -q "${JAR_PATH}" -d "${OUTPUT_PATH}"
        find "${OUTPUT_PATH}" -depth -type f -exec codesign -s "$CODESIGN_IDENTITY" "${CODESIGN_ARGS[@]}" {} \;
        rm "${JAR_PATH}"
        pushd "${OUTPUT_PATH}" > /dev/null || exit 2
        zip -qr ../"${JAR_FILENAME}" *
        popd > /dev/null ||exit 2
        rm -r "${OUTPUT_PATH}"
        codesign -s "$CODESIGN_IDENTITY" $JAR_PATH
        if [ $DEBUG ]; then
            echo "Done with $JAR_PATH"
        fi
    echo "Deep signing jar files done"
    done
}

# collect input
while getopts ":p:s:" o; do
    case "${o}" in
        s)
            CODESIGN_IDENTITY=${OPTARG}
            ;;
        p)
            INPUT=${OPTARG}
            ;;
        :)
            echo "Invalid option: $OPTARG requires an argument" 1>&2
            exit_abnormal
            ;;    
        *)
            echo "Invalid option: $OPTARG" 1>&2
            exit_abnormal
            ;;
    esac
done
shift "$((OPTIND-1))"

if [ $DEBUG ]; then
    echo "Input params: identity: $CODESIGN_IDENTITY, input: $INPUT"
fi

# verify input
if [ "$CODESIGN_IDENTITY" = "" ] || [ "$INPUT" = "" ]; then                 # If $NAME is an empty string,
    echo "Arguments required" 1>&2
    exit_abnormal
fi

# check if .app
if [ "${INPUT: -4}" == ".app" ]

    then
        codesign_jre "$INPUT"
        # Sign the launcher executable with entitlements
        echo "Code-signing launchers ${1}$JRE_PATH_IN_APP"
        find "$INPUT/Contents/MacOS" -type f -name "*.sh" -exec codesign -s "$CODESIGN_IDENTITY" "${CODESIGN_ARGS[@]}" --entitlements "${ENTITLEMENTS_LAUNCHER}" {} \;
    else
        deepsign_jar "$INPUT"
fi

#finally, code-sign the base path
echo "Code-signing base package $INPUT"
codesign -s "$CODESIGN_IDENTITY" "${CODESIGN_ARGS[@]}" "$INPUT"