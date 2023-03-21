#include <iostream>

#include <imgui.h>
#include "imgui_internal.h"
#include <GLFW/glfw3.h>
#include <GL/gl.h>

#include "../../include/keyholder/main.h"

ImGuiWindowFlags editor_window_flags = ImGuiWindowFlags_NoDecoration | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoBackground | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoScrollWithMouse | ImGuiWindowFlags_MenuBar;

bool editor_window()
{
    int frame_width = 700;
    int frame_height = 500;

    const GLFWvidmode* mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    if(is_editor_loaded == false)
    {
        glfwSetWindowSize(window, frame_width, frame_height); //resize
        glfwSetWindowPos(window, (mode->width - frame_width)/2, (mode->height - frame_height)/2); //center
        is_editor_loaded = true;
    }
    
    const ImGuiViewport* viewport = ImGui::GetMainViewport();
    ImGui::SetNextWindowPos(viewport->WorkPos);
    ImGui::SetNextWindowSize(viewport->WorkSize);

    ImGui::Begin("Main", &login_or_editor, editor_window_flags);

    // MenuBar
    ImGui::BeginMenuBar();

    if (ImGui::BeginMenu("Account"))
    {
        if (ImGui::MenuItem("Add new account"))
        {

        }

        ImGui::Spacing();

        if (ImGui::MenuItem("Edit selected account"))
        {

        }

        ImGui::Spacing();

        if (ImGui::MenuItem("Remove selected account"))
        {

        }

        ImGui::EndMenu();
    }

    if (ImGui::BeginMenu("Help"))
    {
        if (ImGui::MenuItem("About"))
        {

        }

        ImGui::Spacing();

        if (ImGui::MenuItem("Locate database"))
        {

        }

        ImGui::Spacing();

        if (ImGui::MenuItem("Shortcuts"))
        {
            
        }

        ImGui::EndMenu();
    }

    ImGui::EndMenuBar();

    ImGui::End();

    return login_or_editor;
}