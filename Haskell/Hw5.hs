module Homework where

-- Стоянов Дима А3201

-- Задание:
-- Написать функцию listWords :: Dictionary -> [String],
-- которая выдает список всех слов в заданном словаре.


-- Заведем вспомогательную функцию, котороая будет выдавать список слов поддерева бора.
-- Терминальное условие обхода - поддерево без сыновей
-- В таком случае результат - пустой список списков
-- Так как результатом разбора сыновей будет список строк
-- Нам нужно добавить текущий символ узла к каждой строке этого списка
-- Для этого используем concatMap, так же чтобы убрать излишнюю вложенность.
-- Чтобы разбор слов работал для всего бора, а не отдельного поддерева
-- нужно запустить вспомогательную функцию на всех поддеревьях
-- и соеденить результат

  data Trie = Empty | Node Char [Trie]
  type Dictionary = [Trie]


  listWords :: Dictionary -> [String]
  listWords = concatMap (\y -> listWords' y) where
    listWords' :: Trie -> [String]
    listWords' (Empty) = [[]]
    listWords' (Node c t) = concatMap (\y -> map (\x -> c:x) (listWords' y) ) t

-- Тесты
  main = [
        listWords [Node 'b' [Node 'i' [Node 't' [Empty,
                                       Node 'e' [Empty]]],
                             Node 'y' [Node 't' [Node 'e' [Empty]]]],
                   Node 's' [Node 'i' [Node 't' [Node 'e' [Empty]]]]]
        == ["bit", "bite", "byte", "site"],
        listWords [Node 'a' [Node 'b' [Node 'a' [Node 'c' [Node 'a' [Node 'b' [Node 'a' [Empty]]],
                                                           Node 'b' [Node 'b' [Node 'a' [Empty]]],
                                                           Empty],
                                                 Empty],
                                       Node 'c' [Node 'a' [Node 't' [Empty]],
                                                 Node 'u' [Node 't' [Empty]]]]],
                   Node 'z' [Empty]]
        == ["abacaba", "abacbba", "abac", "aba", "abcat", "abcut", "z"],
        listWords [] == [],
        listWords [Empty] == [""],
        listWords [Node 'p' [Node 'i' [Node 'n' [Node 'g' [Empty]]],
                             Node 'o' [Node 'n' [Node 'g' [Empty]]]],
                   Node 's' [Node 't' [Node 'a' [Node 'c' [Node 'k' [Empty]]]],
                             Node 'l' [Node 'a' [Node 'c' [Node 'k' [Empty]]]]],
                   Node 'z' [Node 'i' [Node 'p' [Empty]]]]
        == ["ping", "pong", "stack", "slack", "zip"]
        ]
