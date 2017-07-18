import Data.List
import Debug.Trace
import System.Environment


main = do
  args <- getArgs
  putStrLn $ show $ run (read $ head args) (read $ args !! 1) (read $ args !! 2)



run x y n = step x y n where
  emptyUsed :: Int -> [[Bool]]
  emptyUsed n = replicate n $ replicate n False
  step x y n = step' x  y n x y 1 (set x y True $ emptyUsed n)



set :: Int -> Int -> a -> [[a]] -> [[a]]
set x y value array = take x array ++ (set' (array !! x) y value) : drop (x+1) array where
  set' :: [a] -> Int -> a -> [a]
  set' array x value = take x array ++ value : drop (x+1) array


step' :: Int -> Int -> Int -> Int -> Int -> Int -> [[Bool]] -> Bool
step' startX startY n x y count used | count == n^2 && elem (startX, startY) (justCoords) = True
                                     | otherwise = go coords where
  justCoords = [(x+2, y+1),  (x-2, y+1), (x+2, y-1), (x-2, y-1), (x+1, y-2), (x-1, y-2), (x+1, y+2), (x-1, y+2)]
  coords =  filter (\(elX,elY) -> elX >= 0 && elY >= 0 && elX < n && elY < n && used !! elX !! elY == False) justCoords

  go :: [(Int, Int)] -> Bool
  go [] = False
  go ((elX, elY):xs) | trace ("+ " ++ show (elX, elY)) $ (step' startX startY n elX elY (count + 1) (set elX elY True used)) = True
                     | otherwise = trace "-" $ go xs
