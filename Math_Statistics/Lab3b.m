clc
clear


n = 10^6;
m = 100;

errors = 0;
for i = 1:10
  
    X = sort(unifrnd(1, 2, n, 1));
    a = min(X);# mean for norm
    b = max(X);# std for norm
    Y = hist(X, m);
    h = (X(n) - X(1)) / m;
    hStep = X(1) : h : X(end) - h;
    y1 = unifcdf(hStep, a, b);
    hRight = X(1) + h : h : X(end);
    y2 = unifcdf(hRight, a, b);
    p = (y2 - y1);
    d = sum(((Y - n* p) .^ 2) ./ (n*p));
    kvant = chi2inv(0.95, m - 3);
    if (d < kvant)
      errors++;
    endif
endfor

printf("First kind of error: %d\n", 1 - errors / i)
