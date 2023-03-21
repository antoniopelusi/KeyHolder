include(../cmake/CPM.cmake)

# ----------------------- SDL2 -----------------------#

CPMAddPackage(
  NAME glfw
  GIT_TAG 3.3.6
  GITHUB_REPOSITORY glfw/glfw
)

# ----------------------- IMGUI -----------------------#

CPMAddPackage(
  NAME imguilib
  GIT_TAG origin/master
  GITHUB_REPOSITORY ocornut/imgui
)

add_library(imgui STATIC)

target_sources(
  imgui
  PRIVATE ${imguilib_SOURCE_DIR}/imgui.h
          ${imguilib_SOURCE_DIR}/imgui.cpp
          ${imguilib_SOURCE_DIR}/imconfig.h
          ${imguilib_SOURCE_DIR}/imgui_demo.cpp
          ${imguilib_SOURCE_DIR}/imgui_draw.cpp
          ${imguilib_SOURCE_DIR}/imgui_internal.h
          ${imguilib_SOURCE_DIR}/imgui_tables.cpp
          ${imguilib_SOURCE_DIR}/imgui_widgets.cpp
          ${imguilib_SOURCE_DIR}/imstb_rectpack.h
          ${imguilib_SOURCE_DIR}/imstb_textedit.h
          ${imguilib_SOURCE_DIR}/imstb_truetype.h
          ${imguilib_SOURCE_DIR}/backends/imgui_impl_glfw.cpp
          ${imguilib_SOURCE_DIR}/backends/imgui_impl_glfw.h
          ${imguilib_SOURCE_DIR}/backends/imgui_impl_opengl3.cpp
          ${imguilib_SOURCE_DIR}/backends/imgui_impl_opengl3.h
          ${imguilib_SOURCE_DIR}/backends/imgui_impl_opengl3_loader.h
)

target_include_directories(
  imgui
  PUBLIC ${imguilib_SOURCE_DIR}/
  PUBLIC ${imguilib_SOURCE_DIR}/backends
)

find_package(OpenGL REQUIRED)

target_link_libraries(imgui PUBLIC glfw ${CMAKE_DL_LIBS} ${OPENGL_LIBRARIES})
