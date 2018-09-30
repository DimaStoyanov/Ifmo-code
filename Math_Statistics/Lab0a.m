# Лабораторная работа №0
# Задание A
# Вариант 17
clc
clear

a = pi;
c = 1.4;
g = 0.95;
T = norminv((1+g)/2)
k = 5;

nList = [10 ^ 4, 10 ^ 6];

for i = 1 : length(nList)
  n = nList(i);
  fprintf('\nCalculate for n = %i\n', n);
  x = rand(n, k);
  y = x.^a;
  res = sum(y');
  v = mean(res < c)

  d1 = std(res < c) * T / sqrt(n)
  d2 = T * sqrt(v * (1 - v) / n) 
end