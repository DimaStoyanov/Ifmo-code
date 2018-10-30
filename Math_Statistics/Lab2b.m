clc
clear 

a1 = 1;
s1 = 1;
n = 10 ^ 4;
m =  100;

x = sort(normrnd(a1, s1, n, m));
F_n = 1 / n : 1 / n : 1;
F_r = normcdf(x, a1, s1);
col = sqrt(n) * max(max(abs(F_r - F_n'), abs(F_r - F_n' - 1 / n)));
k = mean(col > 1.36)
 
smirnov = 1 / (12*n) + sum((F_r - F_n' + 1 / (2*n)) .^ 2);
s = mean(smirnov > 0.46)

fprintf("\n\nError of the first kind\n")
for m = 100:200:1000
  fprintf("Calculate for m=%d\n", m)
  x = sort(normrnd(a1, s1, n, m));
  F_r = normcdf(x, a1, s1);
  
  col = sqrt(n) * max(max(abs(F_r - F_n'), abs(F_r - F_n' - 1 / n)));
  k = mean(col > 1.36)
 
  smirnov = 1 / (12*n) + sum((F_r - F_n' + 1 / (2*n)) .^ 2);
  s = mean(smirnov > 0.46)
endfor

m = 100;
fprintf("\n\nError of the second kind\n")
for l = 0:0.01:0.1
  fprintf("Calculate for l=%d\n", l)
  F_r = normcdf(x, a1, s1 + l);
  
  col = sqrt(n) * max(max(abs(F_r - F_n'), abs(F_r - F_n' - 1 / n)));
  k = 1 - mean(col > 1.36)
 
  smirnov = 1 / (12*n) + sum((F_r - F_n' + 1 / (2*n)) .^ 2);
  s = 1 - mean(smirnov > 0.46)
endfor
