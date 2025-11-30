#!/usr/bin/env bash

FOLDER="$1"
shift

CONFIGS=$@
SRC=".config/templates/${FOLDER}/application.properties"
DST=".config/deployment/${FOLDER}/config/application.properties"

mkdir -p "$(dirname $DST)"
cp "${SRC}" "${DST}"

echo "Setting up \"${FOLDER}\" properties..."

for config in ${CONFIGS[@]}; do
    value="$(systemd-ask-password --emoji=no "${config}:")"

    if [ -z "$value" ]; then
        >&2 echo "bad value for ${config}"
        rm -f "${DST}"
        exit 1
    fi

    sed -i 's/'"<${config}>"'/'"$value"'/g' "${DST}"
done
