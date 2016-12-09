#include <cxxabi.h>
#include "format.h"


bool check_flags(char c) {
    return c == '-' || c == '+' || c == ' ' || c == '#' || c == '0';
}

bool check_width(char c) {
    return c == '*' || isdigit(c);
}

bool check_precision(char c) {
    return c == '.';
}

void check_specifier(const std::string &fmt, size_t &index) {
    std::string s;
    s.push_back(fmt[index]);
    if (index + 1 < fmt.length() && fmt[index + 1] == fmt[index]) {
        s.push_back(fmt[index + 1]);
    }
    if (s == "l" || s == "ll" || s == "h" || s == "hh"
        || s == "j" || s == "z" || s == "t" || s == "L") {
        index += s.length();
    }
}

Format get_format(const std::string &fmt, size_t &index) {
    char cur_char = fmt[index++];
    Format format;
    if (cur_char == a || cur_char == A || cur_char == c || cur_char == d || cur_char == i || cur_char == e || cur_char == E || cur_char == f ||
        cur_char == F || cur_char == g || cur_char == G || cur_char == o || cur_char == s || cur_char == x || cur_char == X || cur_char == p ||
        cur_char == n || cur_char == u || cur_char == automatc) {
        format = (Format) cur_char;
        return format;
    }
    throw std::invalid_argument("Format: invalid format " + cur_char);
}
// Check has this format extra arg for precision or width
bool has_arg(const std::string &fmt, size_t &index) {
    if (fmt[index] == '*') {
        index++;
        return true;
    }
    while (isdigit(fmt[index])) {
        index++;
    }
    return false;
}

// if there are format required formatting some arg, this func processes it
std::string read_format_with_args(const std::string &fmt, size_t &index, bool &width_arg, bool &precision_arg,
                                  Format &format) {
    size_t start = index++, len = fmt.length();
    while (index < len && check_flags(fmt[index])) {
        index++;
    }
    if (check_width(fmt[index])) {
        width_arg = has_arg(fmt, index);
    }
    if (check_precision(fmt[index])) {
        precision_arg = has_arg(fmt, ++index);
    }
    check_specifier(fmt, index);
    format = get_format(fmt, index);
    return fmt.substr(start, index - start);
}

std::string read_format(const std::string &fmt, size_t &index, bool &width_arg, bool &precision_arg,
                        Format &f) {
    size_t len = fmt.length(), start = index;
    char c;
    for (index; index < len; index++) {
        c = fmt[index];
        if (c != '%') {
            continue;
        } else if (index != start) {
            return fmt.substr(start, index - start);
        }
        if (index + 1 < len && fmt[index + 1] == '%') {
            index += 2;
            return "%";
        }
        return read_format_with_args(fmt, index, width_arg, precision_arg, f);
    }
    return fmt.substr(start, index - start);
}

std::string replace_format(const std::string &fmt, int &wp) {
    size_t first = fmt.find_first_of('*');
    std::string result = fmt.substr(0, first) + std::to_string(wp) + fmt.substr(first + 1, fmt.length());
    return result;
}


