#include <iostream>

#include "imgui.h"
#include "imgui_internal.h"
#include <GLFW/glfw3.h>
#include <GL/gl.h>

#include "../../include/keyholder/main.h"

ImGuiWindowFlags login_window_flags = ImGuiWindowFlags_NoDecoration | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoBackground | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoScrollWithMouse;

void CreateDatabase()
{

}

void LoadDatabase()
{
    
}

void BackGroundCentered()
{
    auto windowWidth = ImGui::GetWindowSize().x;

    // Load background
    GLuint background = 0;
    int background_width = 0;
    int background_height = 0;

    bool ret = LoadTextureFromFile("../../assets/images/icon.png", &background, &background_width, &background_height);
    
    background_width -= 100;
    background_height -= 100;
    
    IM_ASSERT(ret);

    // calculate centre of window for button
    ImGui::SetCursorPosX((windowWidth - background_width) / 2);
    ImGui::SetCursorPosY(-5);


    ImGui::Image((void*)(intptr_t)background, ImVec2(background_width, background_height));
}

void ButtonsCentered()
{
    ImVec2 button_size = ImGui::CalcItemSize(ImVec2{65, 25}, 0.0f, 0.0f);

    // obtain size of window
    ImVec2 avail = ImGui::GetWindowSize();

    // calculate centre of window for button
    ImVec2 centre_position_for_button
    {
        // we have two buttons, so twice the size - and we need to account for the spacing in the middle
        (avail.x - button_size.x * 2 - ImGui::GetStyle().ItemSpacing.x - 55) / 2,
        (avail.y - button_size.y) / 2
    };

    // tell Dear ImGui to render the button at the new pos
    ImGui::SetCursorPos(centre_position_for_button);

    if(ImGui::Button("Create", button_size))
    {
        CreateDatabase();
    }
        ImGui::SameLine();

    for(int i = 0; i<7; i++)
    {
    
        ImGui::Spacing();
        ImGui::SameLine();
    }

    if(ImGui::Button("Load", button_size))
    {
        LoadDatabase();
        login_or_editor = false;
    }
}

bool login_window()
{
    int frame_width = 400;
    int frame_height = 200;

    const GLFWvidmode* mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    if(is_login_loaded == false)
    {
        glfwSetWindowSize(window, frame_width, frame_height); //resize
        glfwSetWindowPos(window, (mode->width - frame_width)/2, (mode->height - frame_height)/2); //center
        is_login_loaded = true;
    }

    const ImGuiViewport* viewport = ImGui::GetMainViewport();
    ImGui::SetNextWindowSize(viewport->WorkSize);
    ImGui::SetNextWindowPos(viewport->WorkPos);

    ImGui::Begin("Login", &login_or_editor, login_window_flags);

    BackGroundCentered();

    ButtonsCentered();

    ImGui::End();

    return login_or_editor;
}