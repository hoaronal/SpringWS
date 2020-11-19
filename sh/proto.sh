#!/usr/bin/env bash
export HOME_PATH=H:\project\PSP\SpringWS
export DA_SERVER_HOME_PATH=${HOME_PATH}
export DA_AGENT_HOME_PATH=${HOME_PATH}\client\proto
export OUTPUT_JAVA_PATH=${DA_SERVER_HOME_PATH}\src\main\java
export OUTPUT_JS_PATH=${DA_AGENT_HOME_PATH}
export PROTO_PATH=${DA_SERVER_HOME_PATH}\src\main\resources\protobuf

protoc --java_out=H:\project\PSP\SpringWS\src\main\java -I H:\project\PSP\SpringWS\src\main\resources\protobuf H:\project\PSP\SpringWS\src\main\resources\protobu\*.proto
protoc --js_out=import_style=commonjs,binary:${OUTPUT_JS_PATH} -I ${PROTO_PATH} ${PROTO_PATH}\*.proto