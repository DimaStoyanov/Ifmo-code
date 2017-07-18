#!/bin/bash

# Написать скрипт, который ищет последовательность
# введенных с клавиатуры символов в текстовом файле и выводит все
# строки, содержащие эту последовательность в файл.
# Текстовый файл для поиска выбрать или создать самостоятельно.
# (команда grep)

# Скрипт принимает 1 аргументом файл, в котором нужно искать шаблон или ищет в дефолтном файле
if [[ $# > 0 ]]; then file_name=$1; else file_name=game_of_thrones.fb2; fi
if [[ ! -e $file_name ]]; then echo File $file_name doesn\'t exist; exit; fi
echo "Current document : $file_name"
read -p "Enter matching pattern: " pattern
result=`grep $pattern $file_name`
if [[ -z $result ]]; then echo No matches with pattern "$pattern"; else echo $result; fi
