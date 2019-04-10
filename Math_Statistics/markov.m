clc
clear

n = 8;
p = 0.45;
q = 1 - p;
d = zeros(n + 1, 1);
d(1) = q;
d(n + 1) = p;

P = diag(p * ones(n, 1), 1) + diag(q * ones(n, 1), -1);
P(1, :) = eye(1, n + 1);
P(n + 1, :) = flip(eye(1, n + 1));
P

first = ceil(rand(1, 1) * (n - 1));
P0 = zeros(1, n+1);
P0(first+1) = 1;
P0

P_lim_pract = P0 * P^100

Pinf = ((q/p)^first- (q/p)^n) / (1 - (q/p)^n)
Qinf = (1-(q/p)^first) / (1-(q/p)^n)

for i=1:100
  Pd(i,:) = P0 * P^(10+3*i);
endfor
figure(1)
plot(Pd), grid

steps = 100;
path(1) = first;
for i=2:steps
  if(path(i-1) == 0 || path(i -1) == n)
    path(i) = path(i -1);
   else
    if(rand() > p)
      path(i) = path(i - 1) - 1;
    else
      path(i) = path(i - 1) + 1;
    endif
   endif
endfor
i = 1:1:steps;
figure(2)
plot(i, path, '-*', i, 0, i, n)
