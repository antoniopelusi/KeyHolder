#pragma once

#include <GLFW/glfw3.h>

//declaration of global variables
extern bool login_or_editor;

extern bool is_login_loaded;
extern bool is_editor_loaded;

extern GLFWwindow* window;

extern unsigned char* img;

bool LoadTextureFromFile(const char* filename, GLuint* out_texture, int* out_width, int* out_height);