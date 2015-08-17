########################################
# Updates Version numbers and builds ForgeGradle
########################################

#Get build type
#buildtype=$(grep -oP "<build>(.*)</build>" config.xml | cut -d '>' -f 2 | cut -d '<' -f 1)

mkdir -p ./wiki
cd ./wiki

# Clone wiki https://github.com/GenDeathrow/Skills.wiki.git
git clone git@github.com:GenDeathrow/CutScenes-1.7.10.wiki.git ./

#Get Current Version from Github
curversion=$(grep -oP "<${build_text}>(.*)</${build_text}>" Version_Info.md | cut -d '>' -f 2 | cut -d '<' -f 1)
buildtype=$(grep -oP "<build>(.*)</build>" Version_Info.md | cut -d '>' -f 2 | cut -d '<' -f 1)

cd ../

# Replace with update version in config.xml
grep -lR -e "<version>.*<\/version>" config.xml | xargs sed -i "s/<version>.*<\/version>/<version>${curversion}<\/version>/g"

# Increment Version Number 
eval ./gradlew -q ${buildtype}

#Get new Version number
newversion=$(grep -oP "<version>(.*)</version>" config.xml | cut -d '>' -f 2 | cut -d '<' -f 1)

#Replace all ver keys with new Number
grep -lRr -e $ver_key * | xargs sed -i "s/$ver_key/$newversion/g"

#Build Forge
./gradlew clean setupCIWorkSpace build

. ./make_logs

#upload Curse
#. ./curse_upload

#Move back to wiki to update
cd ./wiki

full_download_link="${download_link}${newversion}.jar"
full_build_link="${build_link}${DRONE_BUILD_NUMBER}"

echo ${full_download_link}
echo ${full_build_link}

# Replace with update version in  wiki
grep -lR -e "<${build_text}>.*<\/${build_text}>" *| xargs sed -i "s/<${build_text}>.*<\/${build_text}>/<${build_text}>${newversion}<\/${build_text}>/g"

# Replace our build status back to default
grep -lR -e "<build>.*<\/build>" *| xargs sed -i "s/<build>.*<\/build>/<build>Patch<\/build>/g"

#Replace old date with new one
grep -lR -e "<${date_text}>.*</${date_text}>" * | xarg sed -i "s/<${date_text}>.*<\/${date_text}>/<${date_text}>${cur_date}<\/${date_text}>/g"

#Replace old download link with new one
grep -lR -e "\[downloads\-1\.7\.10\]:.*" * | xargs sed -i "s#\[downloads\-1\.7\.10\]:.*#\[downloads\-1\.7\.10\]: ${full_download_link}#g"

echo grep -lR - e "\[downloads\-1\.7\.10\]:.*" *

#Replace old build link with new one
grep -lR -e "\[build\-1\.7\.10\]:.*" * | xargs sed -i "s#\[build\-1\.7\.10\]:.*#\[build\-1\.7\.10\]: ${full_build_link}#g"

#Commit the new page
git add .
git commit -m "Updated version"
git push -u origin master

cd ../

. ./wp-post




