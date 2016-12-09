cmake_minimum_required(VERSION 3.3)
project(task4)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES Mathlog.src.Test.cpp)
add_executable(Mathlog.src.Test ${SOURCE_FILES})
add_library(format format.cpp)

target_link_libraries(Mathlog.src.Test format)

install(TARGETS format DESTINATION lib)
install(FILES format.h DESTINATION include)