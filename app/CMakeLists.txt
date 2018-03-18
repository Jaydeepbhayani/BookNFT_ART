# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set (EXT_LIBS "/media/sayg/SayG/JaydeepWork/Projects/FoodServer/ExternalLibs/")
set (LOCAL_PATH ${CMAKE_CURRENT_LIST_DIR})
message("Current dir --> " ${LOCAL_PATH})

add_library(nftBookNative SHARED src/main/cpp/nftBook.cpp src/main/cpp/ARMarkerNFT.c src/main/cpp/trackingSub.c src/main/cpp/VirtualEnvironment.c)

set (ARTOOLKIT_LIBS ar2 kpm util eden argsub_es armulti arosg ar aricp jpeg arvideo cpufeatures)

foreach(module ${ARTOOLKIT_LIBS})
    set(LOCAL_MODULE ${module})
    #message("module name --> " ${LOCAL_MODULE})
    set(LOCAL_SRC_FILES lib${module}.a)
    #message("module lib --> " ${LOCAL_SRC_FILES})
    add_library( ${LOCAL_MODULE} STATIC IMPORTED )
    set_target_properties( ${LOCAL_MODULE} PROPERTIES IMPORTED_LOCATION ${EXT_LIBS}/obj/local/${ANDROID_ABI}/${LOCAL_SRC_FILES} )
endforeach(module)

include_directories(${EXT_LIBS}/include/)

set (ARTOOLKIT_LIBS OpenThreads osg osgAnimation osgDB osgFX osgGA osgParticle osgPresentation osgShadow osgSim osgTerrain osgText osgViewer osgUtil osgVolume osgWidget osgdb_osg osgdb_ive osgdb_jpeg osgdb_gif gif osgdb_tiff tiff osgdb_bmp osgdb_png png osgdb_tga osgdb_freetype ft2 osgdb_deprecated_osg osgdb_deprecated_osganimation osgdb_deprecated_osgfx  osgdb_deprecated_osgparticle osgdb_deprecated_osgshadow osgdb_deprecated_osgsim osgdb_deprecated_osgterrain osgdb_deprecated_osgtext osgdb_deprecated_osgviewer osgdb_deprecated_osgvolume osgdb_deprecated_osgwidget)

foreach(module ${ARTOOLKIT_LIBS})
    set(LOCAL_MODULE ${module})
    set(LOCAL_SRC_FILES lib${module}.a)
    add_library( ${LOCAL_MODULE} STATIC IMPORTED )
    set_target_properties( ${LOCAL_MODULE} PROPERTIES IMPORTED_LOCATION ${EXT_LIBS}/obj/local/${ANDROID_ABI}/${LOCAL_SRC_FILES} )
endforeach(module)

include_directories(${EXT_LIBS}/include/android/)

add_library( curl STATIC IMPORTED )
set_target_properties( curl PROPERTIES IMPORTED_LOCATION ${EXT_LIBS}/curl/libs/${ANDROID_ABI}/libcurl.a )
include_directories(${EXT_LIBS}/curl/include/)


find_library( log-lib log )
find_library(glesv1 GLESv1_CM)
find_library(lz z)

target_link_libraries( nftBookNative ${log-lib} ${glesv1} ${lz}

                        -Wl,--whole-archive ar -Wl,--no-whole-archive
                        ar2 kpm util eden argsub_es armulti arosg aricp cpufeatures jpeg arvideo

                        # OSG libs
                        osgdb_osg osgdb_ive osgdb_jpeg osgdb_gif gif osgdb_tiff tiff osgdb_bmp osgdb_png png osgdb_tga osgdb_freetype ft2 osgAnimation osgFX osgParticle osgPresentation osgShadow osgSim osgTerrain osgText osgVolume osgWidget osgViewer osgGA osgDB osgUtil osgdb_deprecated_osg osgdb_deprecated_osganimation osgdb_deprecated_osgfx  osgdb_deprecated_osgparticle osgdb_deprecated_osgshadow osgdb_deprecated_osgsim osgdb_deprecated_osgterrain osgdb_deprecated_osgtext osgdb_deprecated_osgviewer osgdb_deprecated_osgvolume osgdb_deprecated_osgwidget osg OpenThreads

                        curl)