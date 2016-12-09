#ifndef RATIONAL_H
#define RATIONAL_H

class rational {
	int num;
	int denom;
	int calculateGCD(int x, int y);
	int abs(int x);
public:
	rational(int num);
	rational(int num, int denom);
	int getNum() const;
	int getDenom() const;
	rational const operator +(rational const &a) const;
	rational const operator -(rational const &b) const;
	rational const operator *(rational const &c) const;
	rational const operator /(rational const &d) const;
};

#endif