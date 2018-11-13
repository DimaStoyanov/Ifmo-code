clc
clear 


function [means] =  calc (X, means_theor, medians_theor, semisums_theor)
  means = mean(X, 2);
  means_std = std(means);
  medians = median(X, 2);
  medians_std = std(medians);


  varSeries = sort(X, 2);
  semisums = (varSeries(:, 1) + varSeries(:, end)) ./ 2;
  semisums_std = std(semisums);

  means = [means_std, means_theor; medians_std, medians_theor; semisums_std, semisums_theor];
  return
endfunction


a = 0;
sigma = 2;
n = 100;
m = 50;
X_norm = normrnd(a, sigma, m, n);
printf("Normal distribution:\n")
ans = calc(X_norm, sigma / sqrt(n), sigma * sqrt(pi / 2 / n), 0.65 * sigma / sqrt(log(n)));

n = 10_000;
X_norm = normrnd(a, sigma, m, n);
ans = [ans, calc(X_norm, sigma / sqrt(n), sigma * sqrt(pi / 2 / n), 0. * sigma / sqrt(log(n)))]



a = 0;
d = 2;
n = 100;
X_unif = unifrnd(a - d / 2, a + d / 2, m, n);
printf("Uniform distribution:\n")
ans = calc(X_unif, d/2/sqrt(3 * n), d /2/sqrt(n), d / sqrt(2) / n);
n = 10_000;
X_unif = unifrnd(a - d / 2, a + d / 2, m, n);
ans = [ans, calc(X_unif, d/2/sqrt(3 * n), d /2/sqrt(n), d / sqrt(2) / n)]

a = 0;
u = 2;
n = 100;
X_lapl = a + exprnd(u, m, n) - exprnd(u,m,n);
printf("Laplace distribution:\n")
ans = calc(X_lapl, u*sqrt(2/n), u/sqrt(n), 0.95 * u);
n = 10_000;
X_lapl = a + exprnd(u, m, n) - exprnd(u,m,n);
ans = [ans, calc(X_lapl, u*sqrt(2/n), u/sqrt(n), 0.95 * u)]