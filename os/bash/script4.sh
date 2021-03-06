#!/bin/bash

# Написать скрипт, который копирует под новым именем все
# файлы, имеющие имена начинающиеся на «с». (команда cp)

# Ищем в текущем каталоге файлы с префиксом "с".
# Если их не сущесвтует, не выводим ошибку комманды ls в стандартный поток
files=`ls c* 2>/dev/null`
if [[ -z $files ]]; then echo There are no files with prefix \"c\"; exit; fi
# Цикл по всем фалйам, начинающимся на "c"
for i in $files
do
	# Копируем файл, добавляя к его имени префикс "copy"
	cp $i copy$i
done
