#!/bin/bash

echo "=== CLJML Mod Template Setup ==="
echo ""
read -p "Enter your mod ID (lowercase, no spaces): " MOD_ID
read -p "Enter your mod name (display name): " MOD_NAME
read -p "Enter your group ID (e.g., com.yourname): " GROUP_ID
read -p "Enter your namespace prefix (e.g., yourname): " NAMESPACE

# Update gradle.properties
sed -i "s/mod_id=.*/mod_id=$MOD_ID/" gradle.properties
sed -i "s/mod_name=.*/mod_name=$MOD_NAME/" gradle.properties
sed -i "s/mod_group_id=.*/mod_group_id=$GROUP_ID/" gradle.properties

# Rename source directories
OLD_NAMESPACE="net/sono/cljmlexample"
NEW_NAMESPACE=$(echo "$GROUP_ID/$MOD_ID" | tr '.' '/')

mkdir -p "src/main/clojure/$NEW_NAMESPACE"
mkdir -p "src/main/java/$NEW_NAMESPACE"

# Move Clojure files
if [ -d "src/main/clojure/net/sono/cljmlexample" ]; then
    cp -r src/main/clojure/net/sono/cljmlexample/* "src/main/clojure/$NEW_NAMESPACE/"
    rm -rf src/main/clojure/net/sono
fi

# Update namespace references in Clojure files
find src/main/clojure -name "*.clj" -exec sed -i "s/net\.sono\.cljmlexample/$GROUP_ID.$MOD_ID/g" {} +

# Update build.gradle namespace references
sed -i "s/net\.sono\.cljmlexample\.cljmlexample/$GROUP_ID.$MOD_ID.$MOD_ID/g" build.gradle

echo ""
echo "✓ Setup complete!"
echo "Next steps:"
echo "1. Review gradle.properties"
echo "2. Update your namespace in src/main/clojure/$NEW_NAMESPACE/"
echo "3. Run: ./gradlew build"
echo "4. Delete this setup script: rm setup.sh"