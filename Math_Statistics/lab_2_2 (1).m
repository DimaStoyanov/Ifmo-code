#Ќормальное распределение
#„асть2
clc
clear 
a = 5;
sigma = 2;
n = 10^4;
m = 100;
x = sort(normrnd(a, sigma, n, m));
Fx = normcdf(x, a, sigma);
Fn = 1/n : 1/n : 1;
g = 0.95;
kvK = 1.36;
kvS = 0.46;

#Fx без исменений
for i = 1 : m
  D(i) = sqrt(n) * max(max(abs(Fx(:,i) - (Fn - 1 / n)'), abs(Fx(:,i) - Fn')));
  W(i) = 1/(12*n) + sum((Fx(:,i) - (Fn - 1/(2*n))').^2);
  if (D(i) >= kvK)
    kk(i) = 1;
else kk(i) = 0;
end

if (W(i) >= kvS)
    sk(i) = 1;
else sk(i) = 0;
end
end
  
k = sum(kk)/m;
s = sum(sk)/m;

#Fx с измененными параметрами
l = 0:0.02:0.2
for j = 1:size(l,2)
for r = 1:10
   Fx = normcdf(x, a , sigma + l(j));
for i = 1 : m
  D(i) = sqrt(n) * max(max(abs(Fx(:,i) - (Fn - 1 / n)'), abs(Fx(:,i) - Fn')));
  W(i) = 1/(12*n) + sum((Fx(:,i) - (Fn - 1/(2*n))').^2);
  if (D(i) >= kvK)
    kk(i) = 1;
else kk(i) = 0;
end

if (W(i) >= kvS)
    sk(i) = 1;
else sk(i) = 0;
end
end
  
k(r) = sum(kk)/m;
s(r) = sum(sk)/m;
end
kr(j) = sum(k)/r;
sr(j) = sum(s)/r;
end
ans1 = [kr', sr']

l = 0:0.02:0.2;
for j = 1:size(l,2)
for r = 1:10
   Fx = normcdf(x, a + l(j), sigma);
for i = 1 : m
  D(i) = sqrt(n) * max(max(abs(Fx(:,i) - (Fn - 1 / n)'), abs(Fx(:,i) - Fn')));
  W(i) = 1/(12*n) + sum((Fx(:,i) - (Fn - 1/(2*n))').^2);
  if (D(i) >= kvK)
    kk(i) = 1;
else kk(i) = 0;
end

if (W(i) >= kvS)
    sk(i) = 1;
else sk(i) = 0;
end
end
  
k(r) = sum(kk)/m;
s(r) = sum(sk)/m;
end
kr(j) = sum(k)/r;
sr(j) = sum(s)/r;
end
ans2 = [kr', sr']
