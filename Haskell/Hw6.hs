module Homework where

  import Data.List

  -- Стоянов Дмитрий А3201

  -- Задание:
  -- Написать функцию removeBy :: (k -> Bool) -> Map k v -> Map k v,
  --  которая удаляет из заданного отображения те пары,
  --  ключи которых удовлетворяют критерию, заданному первым аргументом функции.
  --  Например, вызов removeBy (<0) m  удалит из отображения m  те пары, ключи которых меньше нуля.


  -- Реализуем мап с помощью списка пар. Для поиска уже есть готовая функция lookup.
  -- Добавлять элемент будем присоединением элемента к массиву без элемента с таким ключом (чтобы избежать дубликатов)
  -- RemoveBy реализуем через свертку - если элемент удволетворяет предикату игнорируем его, иначе добавляем в лист

  class Map m where
    empty :: m k v
    get :: Eq k => k -> m k v -> Maybe v
    put :: Eq k => (k,v) -> m k v -> m k v
    remove :: Eq k => k -> m k v -> m k v
    keys :: m k v -> [k]
    values :: m l v -> [v]
    removeBy :: (k -> Bool) -> m k v -> m k v
    fromList :: Ord k => [(k,v)] -> m k v
    fromList [] = empty
    fromList ((k,v):xs) = put (k,v) (fromList xs)


  newtype ListMap k v = ListMap {mapList :: [(k,v)]} deriving (Eq, Show)

  instance Map ListMap where
    empty = ListMap []
    get k (ListMap xs) = lookup k xs
    put x@(k,v) m@(ListMap xs) = ListMap $ x:(mapList $ remove k m)
    remove k (ListMap xs) = ListMap $ deleteBy (\(a, _) (b, _) -> a == b) (k, undefined) xs
    keys (ListMap xs) = [k | (k,v)<-xs]
    values (ListMap xs) = [v | (k,v)<-xs]
    removeBy f (ListMap xs) = ListMap (foldr (\x@(k,_) ys -> if f k then ys else x:ys) [] xs)




  -- Задание :
  -- Написать функцию replace :: Ord e => e -> BinHeap e -> BinHeap e,
  -- которая меняет в куче минимальное значение на заданное первым аргументом новое значение и выдает модифицированную кучу.

  -- Чтобы поменять значение в вершине кучи и не потерять свойства кучи,
  -- необходимо выполнить просеивание вниз. Для этого ищем сына с минимальным элементом
  -- Если он больше нового значения ничего делать не нужно - куча уже сбалансирована
  -- Иначе присваиваем текущей вершине значение сына, и балансируем сына рекурсивно (с новым значением)

  -- Для того чтобы в биноминальной кучи поменять значение мин элемента, нужно найти кучу с минимальным элементом.
  -- Для этого просматриваем корневые элементы всех куч.
  -- Далее для найденой кучи запускаем функцию смены значения, а остальные кучи не меняем

  data BinTree e = BinTree {getVal :: e, getSons :: [BinTree e]} deriving (Eq, Ord)
  type BinHeap e = [BinTree e]

  instance Show e => Show (BinTree e) where
    show (BinTree e sons) = show e ++ "->" ++ show sons

  changeMin :: Ord e => e -> BinTree e -> BinTree e
  changeMin el (BinTree _ sons)      | null sons = (BinTree el sons)
                                     | otherwise = if minE > el then (BinTree el sons) else (BinTree minE ((changeMin el minH) : (delete minH sons))) where
      (minE, minH@(BinTree _ minSons)) = foldr (\x@(BinTree curE _) y@(curMin, h) -> if curE < curMin then (curE, x) else y)
        ((getVal $ head sons), head sons) sons

  replace :: Ord e => e -> BinHeap e -> BinHeap e
  replace val heaps = left ++ (changeMin val minH) : (tail right) where
    (left, right) = break (== minH) heaps
    minH  = foldr1 (\cur@(BinTree e1 _) min@(BinTree e2 _)-> if e1 < e2 then cur else min) heaps

  -- Тесты
  emptyMap = empty :: ListMap Int Char

  main = [
    mapList ((put (1, 'a') . put (2, 'c') . put (-5,'z')) emptyMap) == [(1, 'a'), (2, 'c'), (-5,'z')],
    mapList ((put (1, 'a') . put (1, 'q') . put (2, 'd')) emptyMap) == [(1, 'a'), (2, 'd')],
    mapList ((remove 3 . put (25, 'w') . put (3, 'r')) emptyMap) == [(25,'w')],
    (get 13 (fromList (zip [0..20] ['a'..'z']) :: ListMap Int Char)) == (Just 'n'),
    get 10 emptyMap == Nothing,
    keys (removeBy (>5) (fromList (zip [0..24] ['a'..'z']) :: ListMap Int Char)) == [0..5],
    replace 3 [BinTree 24 [], BinTree 7 [BinTree 14 [], BinTree 8 []]] == [BinTree 24 [], BinTree 3 [BinTree 14 [], BinTree 8 []]],
    replace 10 [BinTree 5 [BinTree 7 [BinTree 9 [], BinTree 11 []], BinTree 8 []], BinTree 6 []]
    == [BinTree 7 [BinTree 9 [BinTree 10 [], BinTree 11 []], BinTree 8 []], BinTree 6 []]
    ]
