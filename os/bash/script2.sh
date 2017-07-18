#!/bin/bash

# Написать скрипт, который удаляет все файлы, имеющие имена
# начинающиеся на «a». (команда rm)

# Проверяем наличие таких файлов, скрываем ошибку команды ls и выводим свою
if [[ -z `ls a* 2>/dev/null` ]]; then echo There are no files with prefix \"a\"; exit; fi
rm a*
echo Done
