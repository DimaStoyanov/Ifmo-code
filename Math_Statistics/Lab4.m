clc
clear 

function calc (X, means_theor, medians_theor, semisums_theor)
  means = mean(X, 2);
  means_std = std(means)
  printf("mean_theor=%d\n\n", means_theor)

  medians = median(X, 2);
  medians_std = std(medians)
  printf("medians_theor=%d\n\n", medians_theor)


  varSeries = sort(X, 2);
  semisums = (varSeries(:, 1) + varSeries(:, end)) ./ 2;
  semisums_std = std(semisums)
  printf("semisums_theor=%d\n\n", semisums_theor)

endfunction


a = 0;
sigma = 2;

n = 100;
m = 50;
X_norm = normrnd(a, sigma, m, n);
printf("Normal distribution:\n")
calc(X_norm, sqrt(sigma / n), sqrt(sigma * pi / 2 / n), 0.65 * sqrt(sigma / log(n)))

a = 0;
d = 2;
X_unif = unifrnd(a - d / 2, a + d / 2, m, n);
printf("Uniform distribution:\n")
calc(X_unif, d/2/sqrt(3 * n), d /2/sqrt(n), d / sqrt(2) / n)


a = 0;
u = 2;
X_lapl = a + exprnd(u, m, n) - exprnd(u,m,n);
printf("Laplace distribution:\n")
calc(X_lapl, u*sqrt(2/n), u/sqrt(n), 0.95 * u)