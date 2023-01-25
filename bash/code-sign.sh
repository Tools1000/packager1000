#!/bin/bash

# constants

JRE_PATH_IN_APP="/Contents/PlugIns/jre"

# variables

DEBUG=true

# input

INPUT=""
CODESIGN_IDENTITY=""

# functions

usage() {
    echo "Usage: $0 -i [<codesign-identity>] -p [<input-path-to-sign>]" 1>&2; exit 1;
}

exit_abnormal() {
  usage
  exit 1
}

codesign_jre() {
    # codesign libs files
    echo "Code-signing JRE libs in " "$INPUT"$JRE_PATH_IN_APP
    # selectively sign JRE *lib files
    find "$INPUT"$JRE_PATH_IN_APP -name '*.lib' -exec codesign --force -s "${CODESIGN_IDENTITY}" {} \;
    # find and deep sign .jar files inside the JRE
    deepsign_jar "$INPUT"$JRE_PATH_IN_APP
}

# extracts .jar contents, find *lib files and signes them
deepsign_jar(){
    echo "Deep signing jar files in " ${1}
    # deep codesign jar files
    for FILE_IN_JAR in `find ${1} -name "*.jar"`; do
        if [[ $(unzip -l ${JAR_PATH} | grep '.dylib\|.jnilib') ]]; then
            JAR_FILENAME=$(basename ${JAR_PATH})
            if [ $DEBUG ]; then
                echo "Working on $JAR_FILENAME"
            fi
              OUTPUT_PATH=${JAR_PATH%.*}
            if [ $DEBUG ]; then
                echo "Output path $OUTPUT_PATH"
            fi
            unzip -q "${JAR_PATH}" -d "${OUTPUT_PATH}"
            find "${OUTPUT_PATH}" -name '*lib' -exec codesign --force -s "${CODESIGN_IDENTITY}" {} \;
            rm "${JAR_PATH}"
            pushd "${OUTPUT_PATH}" > /dev/null || exit 2
            zip -qr ../"${JAR_FILENAME}" *
            popd > /dev/null || exit 2
            rm -r "${OUTPUT_PATH}"
        fi
    done
}

# collect input
while getopts ":p:i:" o; do
    case "${o}" in
        i)
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

# verify input

if [ "$CODESIGN_IDENTITY" = "" ] || [ "$INPUT" = "" ]; then                 # If $NAME is an empty string,
    echo "Arguments required" 1>&2
    exit_abnormal
fi

# check if .app
if [ "${INPUT: -4}" == ".app" ]

    then
        codesign_jre
    else
        deepsign_jar
fi

#finally, code-sign the base path
codesign --force -s "${CODESIGN_IDENTITY}" --timestamp --options runtime "${INPUT}"
