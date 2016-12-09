#include "lazy_string.h"

lazy_string::lazy_string(const std::string &string) {
    string_ptr = std::make_shared<std::string>(string);
    start_index = 0;
    string_length = string.length();
}

lazy_string::lazy_string() {
    string_ptr = std::make_shared<std::string>("");
    start_index = string_length = 0;
}


lazy_string::operator std::string() {
    return string_ptr->substr(start_index, string_length);
}

size_t lazy_string::size() const {
    return string_length;
}

size_t lazy_string::length() const {
    return string_length;
}

char lazy_string::at(size_t index) const {
    if (index >= string_length || index < 0)
        throw std::out_of_range("lazy string at index " + index);
    return (*string_ptr)[start_index + index];
}

lazy_string::char_reference lazy_string::at(size_t index) {
    if (index >= string_length || index < 0)
        throw std::out_of_range("lazy string at index " + index);
    return char_reference(this, index);
}

char lazy_string::operator[](size_t index) const {
    return (*string_ptr)[start_index + index];
}

lazy_string::char_reference lazy_string::operator[](size_t index) {
    return char_reference(this, index);
}


std::istream &operator>>(std::istream &ins, lazy_string &string) {
    std::shared_ptr<std::string> buffer = std::make_shared<std::string>();
    ins >> *buffer;
    string.string_ptr = buffer;
    string.start_index = 0;
    string.string_length = buffer->length();
    return ins;
}


std::ostream &operator<<(std::ostream &outs, lazy_string &string) {
    for (unsigned int i = 0; i < string.string_length; i++) {
        outs << (*string.string_ptr)[string.start_index + i];
    }
    return outs;
}

lazy_string lazy_string::substr(size_t start_index, size_t len) {
    if (start_index > string_length || start_index < 0)
        throw std::out_of_range("lazy string at index " + start_index);
    lazy_string substring;
    substring.string_ptr = string_ptr;
    substring.start_index = start_index + this->start_index;
    substring.string_length = start_index + len > string_length ? string_length - start_index : len;
    return substring;
}

lazy_string::char_reference &lazy_string::char_reference::operator=(char c) {
    if (lazy_string_ptr->string_ptr.use_count() > 1) {
        lazy_string_ptr->string_ptr = std::make_shared<std::string>(
                lazy_string_ptr->string_ptr->substr(lazy_string_ptr->start_index, lazy_string_ptr->string_length));
        lazy_string_ptr->start_index = 0;
    }
    (*lazy_string_ptr->string_ptr)[lazy_string_ptr->start_index + index] = c;
    return *this;
}


lazy_string::char_reference::char_reference(lazy_string *string, size_t index)
        : lazy_string_ptr(string), index(index) { }