cmake_minimum_required(VERSION 3.6.1)

set(APP_SRC shuchu.c)

aux_source_directory(. APP_SRC)
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_BINARY_DIR})
add_executable(shuchu ${APP_SRC})
