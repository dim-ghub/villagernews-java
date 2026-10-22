# Villager News Addon Port

A Fabric port of the **Villager News Add-On** for Minecraft Java Edition 26.2.
It brings the original Villager News characters, models, animations, textures,
voice acting, and contextual dialogue to Java Edition while retaining normal
Minecraft villager gameplay.

## Features

- Detailed animated Villager News models converted for Entity Model Features
- Biome, profession, and profession-level villager textures
- The Mayor, Testificate Man, Villager Number 5, Villager Number 9, and
  Villager Unreachable as named characters
- Wooly the Sheep and the Villager News wandering trader
- 2,212 original voice clips across 523 dialogue groups
- Context-aware dialogue for player actions, nearby mobs, weather, dimensions,
  combat, trading, work, sleep, spawning, growth, and other world events
- Multi-part conversations between nearby villagers
- Facial expressions and gestures synchronized with each voice line
- Server-controlled dialogue selection, sound playback, cooldowns, and
  villager behavior
- Speakers look toward the player, entity, block, or villager they are talking
  about
- Removable villager noses, character cosmetics, cosmetic reactions, and
  missing-nose conversations
- Character trades for the Mayor Hat, Testificate Man Helmet, Moustache, and
  Microphone
- Persistent natural spawning for one of each special character in distant
  villages
- A craftable Villager News Handbook
- Optional Mod Menu configuration screen

## Requirements

- Minecraft Java Edition 26.2
- Fabric Loader 0.19.5 or newer
- Fabric API for Minecraft 26.2
- Entity Model Features 3.3.5 or newer
- Entity Texture Features 7.2.1 or newer
- Entity Sound Features 0.8.2 or newer

EMF, ETF, and ESF are external dependencies. This project does not bundle or
modify them.

Mod Menu is optional. When installed, its Configure button opens the Villager
News settings directly. Without Mod Menu, the same settings remain available
in the Villager News Handbook.

## Installation

No prebuilt jars are distributed. You need your own copy of the original
add-on and must build the mod yourself; see
[Building from source](#building-from-source).

1. Build the mod jar as described below.
2. Install Fabric Loader for Minecraft 26.2.
3. Download Fabric API, EMF, ETF, and ESF for the same Minecraft version.
4. Put the dependency jars and your built
   `build/libs/villager-news-addon-port-<version>.jar` (not the `-sources`
   jar) in the Minecraft `mods` folder.
5. Start Minecraft with the Fabric profile.

The dialogue controller runs on the server. For multiplayer, install the mod
and its dependencies on both the server and every connecting client so models,
animations, textures, and sounds are available to everyone.

## Characters

Use a name tag on a villager to select a character model and voice:

| Name tag | Character |
| --- | --- |
| `Mayor`, `Mayor Villager`, or `The Mayor` | Mayor Villager |
| `Testificate Man` | Testificate Man |
| `Villager Number 5` or `Villager #5` | Villager Number 5 |
| `Villager Number 9` or `Villager #9` | Villager Number 9 |
| `Villager Unreachable` or `Can't Catch Me!` | Villager Unreachable |

Name a sheep `Wooly` or `Wooly The Sheep` to use Wooly's model, animations,
and sounds. Ordinary villagers and wandering traders receive their Villager
News appearance and dialogue automatically.

Special characters can also appear naturally as new distant villages are
generated. Each character appears once at a time and becomes eligible to spawn
again after being killed.

## Items

All custom items are available in the **Villager News** creative-mode tab.

Craft the Villager News Handbook from three pieces of paper. It includes the
add-on's overview, special-character and cosmetic guides, settings reference,
social and support pages, and the complete searchable Triggers & Reactions
guide.

Shear an adult villager to remove its nose. Interact with that villager while
holding the nose to return it. The Mayor, Testificate Man, Villager #5, and
Villager #9 sell their matching cosmetics. Cosmetics can be given to ordinary
villagers and removed again with shears.

## Dialogue

Villagers react to what happens around them. They can comment when a player
approaches, stares, changes game mode, wears armor, receives an effect, breaks
or places a block, uses an item, completes a trade, or spawns a villager with a
spawn egg. They also react to their profession, workstation, level, biome,
weather, time of day, nearby entities, damage source, and other villagers.

The server chooses the exact voice variant and broadcasts its matching
animation. Each speaker remains occupied for the real length of the clip,
preventing unrelated lines from overlapping. Conversation partners take turns
and continue looking at each other throughout multi-part exchanges.

## Building from source

This repository contains **no assets from the original add-on**. The models,
textures, voice clips, dialogue, and handbook text are generated at build time
from your own purchased copy of the **Villager News 1.0 Add-On** `.mcaddon`.

### 1. Get the add-on

Buy and download the **Villager News 1.0 Add-On** from its official
creators. You need the `.mcaddon` file
(this port was made against `Villager News 1.0 Add-On (addon).mcaddon`).

### 2. Install the tools

You need:

- Git
- JDK 25
- [Node.js](https://nodejs.org/) 20 or newer (with `npm`)
- [FFmpeg](https://ffmpeg.org/) on your `PATH`, or set `FFMPEG_PATH` to the
  executable

### 3. Clone and point the build at your add-on

```bash
git clone https://github.com/dim-ghub/villagernews-java.git
cd villagernews-java
```

Tell the build where your `.mcaddon` is in one of three ways:

1. Put it in an `addon/` folder at the root of this repository (ignored by Git).
2. Pass it on the command line: `-Paddon=/path/to/addon.mcaddon`.
3. Set the `VNAP_ADDON` environment variable to its path.

For example, on Linux or macOS:

```bash
mkdir addon
cp "/path/to/Villager News 1.0 Add-On (addon).mcaddon" addon/
```

### 4. Build

On Windows:

```powershell
.\gradlew.bat build
```

On Linux or macOS:

```bash
./gradlew build
```

The build extracts the add-on into `build/bedrock-source`, installs the porting
tools' Node.js dependencies, and runs `tools/port-addon.mjs` to write the Java
Edition assets into `build/generated/addon-resources`. The distributable jar is
written to `build/libs`. The build warns you if your add-on's SHA-256 does not
match the version this port was made for (`addon_sha256` in
`gradle.properties`).

Hand-written resources live in `src/main/resources`. The English names and
handbook strings live in `src/main/lang/en_us.json` and are merged with the
generated dialogue subtitles. Never commit generated assets or the `.mcaddon`
itself, and do not redistribute built jars, since they contain the add-on's
assets.

### Troubleshooting

- **"Villager News add-on not found"**: the build could not find your
  `.mcaddon`; check the path or the `addon/` folder.
- **"needs FFmpeg"**: install FFmpeg or set `FFMPEG_PATH`.
- **`npm` or `node` not found**: install Node.js and reopen your terminal.
- **SHA-256 warning**: your add-on is a different version than the one this
  port targets; the build continues, but some assets may be missing or wrong.

### Developer tools

To include the operator-only dialogue test command in a development build, set
`dialogue_test_command=true` in `gradle.properties` before building. Use
`/dialoguetest <1-523>` in game to spawn the matching speaker and subject, play
every variant from that dialogue group, and remove the test actors when each one ends.
Use `/dialoguetest continuous` to run all 523 groups in order. Each group is
announced with its variant number in chat, and the next variant begins one second
after the current voice line finishes.
The setting defaults to `false` for release builds.

Run the asset and dialogue verification after a build with:

```powershell
node tools/verify-port.mjs
```

After a build has extracted the add-on into `build/bedrock-source`, create
a formatted copy of the complete add-on, a dialogue symbol map, a feature
inventory, and a Java dialogue coverage report with:

```powershell
node tools/deobfuscate-addon.mjs
```

The output is written to `build/deobfuscated-bedrock-source/full-addon`.
Wooly's smaller focused source map can also be generated with:

```powershell
node tools/deobfuscate-wooly.mjs
```

The focused output is written to `build/deobfuscated-bedrock-source/wooly`. The
known source symbols are documented in
[`docs/bedrock-deobfuscation/wooly.md`](docs/bedrock-deobfuscation/wooly.md).

## Credits

Villager News and the original add-on assets were created by **Oreville
Studios Ltd** and **Element Animation**.

A huge thank you to the original creators for the Villager News series and
the add-on this port is based on. All of the characters, voice acting,
models, textures, animations, and writing are their work. This project only
converts it to run on Java Edition, which is why it requires your own
purchased copy of the add-on instead of shipping any of it. If you enjoy this
port, please support them by buying the original add-on. The converted models, textures,
animations, and audio remain the property of their respective owners. See
[`LICENSE`](LICENSE) for repository licensing details.
