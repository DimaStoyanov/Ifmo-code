module Homework where

  import Prelude hiding (length)
  import Data.Sequence hiding (reverse, filter)
  import Data.Char (isDigit)
  import Data.List (elemIndices, minimumBy, maximumBy)
  import Debug.Trace (trace)


  -- Стоянов Дима

  -- Задание
  -- Строка содержит натуральные числа, разделенные знаками '+', '-', '*', например, "12+23*2".

  -- Написать функцию addPars :: String -> String, которая добавляет в строку круглые скобки таким образом,
  -- чтобы результат вычисления выражения стал максимальным.
  -- Например, для приведенной строки "12+23*2" результатом будет "((12+23)*2)"
  -- (с точностью до внешней пары скобок).
  -- Порядок выполнения операций в результирующей строке должен определяться только скобками,
  -- приоритеты операций не учитываются.
  -- Функция должна выдавать результат за приемлемое время для строки, содержащей 10-12 операндов.


  -- Будем считать динамику по подотрезкам, то есть рассматривать все возможные
  -- разбиения. Например строку 12+13*2 можно разбить как (12+13)*2 или 12+(13*2)
  -- Если между разбиеним вычитание, то в правой части ищем минимальное значение, а в правой максимальной
  -- Иначе в обоих частях ищем максимальные значения
  -- Поскольку это хаскель, хранить динамику тут не удобно, поэтому просто будем каждый раз считать значени D[i][j]
  -- Так как нам часто нужно обраащаться к определенным индексам выражения, будем хранить выражение в быстро листе

  -- Восстанавливать скобки будем следующим образом. Если на текущем шаге мы нашли лучшее разбиение,
  -- нужно к скобкам левой и правой части добавить скобки этого разбиения
  -- Далее shitcode реализующий это

  data Expression = Node {getNum :: Int, getF :: (Int -> Int -> Int)}
  data Answer = Answer {getValue :: Int, getBrackets :: [((Int,Int), (Int, Int))]} deriving (Show, Eq)

  instance Show Expression where
    show (Node a f) = show a ++ showOp f

  showOp :: (Int -> Int -> Int) -> String
  showOp f | f 1 1 == 2 = "+"
           | f 1 1 == 1 = "*"
           | f 1 1 == 0 = "-"

  -- Проходим по строке, если встречаем цифру, накапливаем в аккумулятор, если операнд, то создаем новый элемент из
  -- накопленного числа и операции
  parseStr :: String -> Seq Expression
  parseStr str = let (lastNum, resultArray) = foldl f ("", []) str in fromList $ reverse $ (Node (read $ reverse lastNum) (+)):resultArray where
    getOperation :: Char -> (Int -> Int -> Int)
    getOperation c | c == '+' = (+)
                   | c == '-' = (-)
                   | c == '*' = (*)
    f (lastNum, resultArray) x | isDigit x = (x:lastNum, resultArray)
                               | otherwise = ("", (Node (read $ reverse lastNum) (getOperation x) ):resultArray )

  -- Динамика
  getMinMax :: Int -> Int -> Seq Expression -> ([Answer] -> Answer) -> Answer
  -- Если подотрезок длины 1 - то делить его уже не нужно
  -- Иначе рассматриваем все разбиения
  getMinMax x y arr cmp | y == 1 = Answer{getValue = getNum $ index arr x, getBrackets = []}
                        | otherwise = Answer{getValue = getValue minValue, getBrackets = currentBrackets:(getBrackets minValue)} where
    values = map mapf parts
    minValue = cmp values
    -- После вычисления значения частей, находим скобки текущего разбиения и добавляем к ответу
    currentBrackets = parts !! (head $ elemIndices minValue values)
    -- Считает значение динамики, применняя операцию на стыке левой и правой частей к значению этих частей
    -- Скобки просто складывает
    mapf ((a, b), (c, d)) = let op = getF (index arr (a + b - 1))
      in let leftPart = getMinMax a b arr $ getCompFunc (+)
      in let rightPart = getMinMax c d arr $ getCompFunc op
      in let newValue = op (getValue leftPart) (getValue rightPart)
      in let newBrackets = (getBrackets leftPart) ++ (getBrackets rightPart)
      in Answer{getValue = newValue, getBrackets = newBrackets}

    -- Если текущая операция минус- нужно искать минимум в массиве значений,иначе максимум
    getCompFunc f | showOp f == "-" = minimumBy compF
                  | otherwise = maximumBy compF
    compF a b = compare (getValue a) (getValue b)
    -- Все возможные разбиения
    -- Например для parts 1 3 = [((1,1), (2,2)), ()(1,2), (3,1))]
    parts = map ( \(a, b) -> ( (x, a), (x + a, b) ) ) $ map (\x -> (x, y-x)) [1..(y-1)]


  -- Обрабатываем значение динамики и добавляем скобки
  addPars :: String -> String
  addPars s = '(' : (init $ concat $ fmap (\(a,b) -> a ++ b) $ insertBrackets) ++ ")" where
    -- Вставляет скобки в выражение
    insertBrackets :: Seq (String, String)
    insertBrackets =  foldl (\xs (a,b) -> adjust (\x -> ('(':(fst x), snd x)) a $ adjust (\x -> ((fst x) ++ ")", snd x)) (a+b-1) xs )
      (fmap (\expr -> (show $ getNum expr, showOp $ getF expr)) $ parseStr s) fixBracketsIndices
    -- Убирает скобки длины 1 и превращает в более удобный список
    fixBracketsIndices :: [(Int, Int)]
    fixBracketsIndices = filter (\(_,x) -> x /= 1) $ concatMap (\(a,b) -> [a,b]) $ (getBrackets calculateBrackets)
    -- Просто вызывает функцию вычисления динамики
    -- сalculateBrackets :: Answer
    calculateBrackets = let arr = parseStr s in getMinMax 0  (length arr) arr $ maximumBy (\a b -> compare (getValue a) (getValue b))

  -- На 12 операндах вычисляет за 5 секунд, на 10 - за 1
  main = [
      addPars "12+23*2" == "((12+23)*2)",
      addPars "12*3-14*6" == "(((12*3)-14)*6)",
      addPars "3*12+13-15*17" == "(((3*(12+13))-15)*17)",
      addPars "32*13-11-23*2" == "((32*(13-(11-23)))*2)",
      addPars "12+43*2-15+13*2+13*6+12*43+1-12+23"  == "((12+43)*((2-15)+(((13*(2+13))*(6+12))*(((43+1)-12)+23))))"
          ]
