#include "rational.h"

rational::rational(int num) 
{
	this->num = num;
	this->denom = 1;
}

rational::rational(int num, int denom) 
{
	int gcd = calculateGCD(num, denom);
	this->num = num / gcd;
	this->denom = denom / gcd;
}

int rational::getNum() const 
{
	return this->num;
}

int rational::getDenom() const 
{
	return this->denom;
}

rational const rational::operator+(rational const &a) const
{
	return rational(this->getNum() * a.getDenom() + this->getDenom() * a.getNum(),
		this->getDenom() * a.getDenom()); 
}
rational const rational::operator-(rational const &b) const 
{
	return rational(this->getNum() * b.getDenom() - this->getDenom() * b.getNum(),
		this->getDenom() * b.getDenom());
}

rational const rational::operator*(rational const &c) const
{
	return rational(this->getNum() * c.getNum(),
		this->getDenom() * c.getDenom());
}

rational const rational::operator/(rational const &d) const 
{
	return rational(this->getNum() * d.getDenom(),
		this->getDenom() * d.getNum());
}

int rational::calculateGCD(int x, int y)
{
	return y == 0 ? 
		(x == 0 ? 0 : rational::abs(x)) :
		rational::abs(rational::calculateGCD(y, x % y));
}

int rational::abs(int x) 
{
	return x < 0? -x: x;
}
