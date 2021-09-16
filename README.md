# garden-internal-opendriveconverter

## ビルド手順＆実行方法

**ビルド**  

Mavenをインストールします。

    sudo apt update
    sudo apt install maven=3.6.0-1~18.04.1

Gitをインストールします。

    sudo apt update 
    sudo apt install git=1:2.17.1-1ubuntu0.8

OpenDRIVEConverterプロジェクトのビルドを実行します。

    cd ~/OpenDRIVEConverter
    git clone https://github.com/open-garden/garden-internal-opendriveconverter.git

pom.xmlファイルの場所でmvn clean packageコマンドを入力すると、プロジェクトをクリーンされ、　　  
ビルドが完了すると、 targetフォルダにビルド成果物（OpenDriveConverter.jar）が生成されます。

    cd ~/OpenDRIVEConverter/garden-internal-opendriveconverter
    mvn clean package

**実行**

引数にRoadEditorで作成したMap情報（Json形式で保存したもの）とOpenDRIVEファイルの生成先を指定して実行します。

    java -jar OpenDriveConverter.jar ＜Jsonファイル名＞ ＜xodrファイル名＞
