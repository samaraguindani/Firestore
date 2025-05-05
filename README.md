# Firestore Example for Android
Este repositório contém um exemplo simples de integração com o Firestore no Android Studio, usando o Firebase como banco de dados em tempo real.

## O que é o Firestore?
Firestore é um banco de dados NoSQL desenvolvido pelo Google, parte do Firebase. Ele permite armazenar e sincronizar dados em tempo real entre os usuários, com suporte para consultas, escalabilidade e segurança.

## Passo a Passo para Integrar o Firestore no Android Studio

### 1. Criar um Projeto no Firebase
- Vá até o [Firebase Console](https://console.firebase.google.com/).
- Crie um novo projeto ou selecione um projeto existente.
- Siga as instruções para configurar o Firebase no seu app Android.

### 2. Adicionar Firebase ao Projeto Android
No **Android Studio**, siga os passos para adicionar o Firebase ao seu projeto:
1. Abra o seu projeto no Android Studio.
2. Vá até **Tools** > **Firebase** para abrir o Firebase Assistant.
3. Selecione **Cloud Firestore** na seção **Firebase Realtime Database**.
4. Clique em **Set up Firestore**. O Firebase Assistant irá guiar você pelo processo para adicionar as dependências necessárias ao seu projeto.

Isso incluirá a configuração do **google-services.json** no seu projeto e as dependências do Firebase no arquivo `build.gradle`.

### 3. Adicionar Dependências no `build.gradle`

No arquivo `app/build.gradle`, adicione as dependências necessárias para usar o Firestore:

```gradle
dependencies {
    implementation 'com.google.firebase:firebase-firestore:24.2.1'
    implementation 'com.google.firebase:firebase-auth:21.0.1'
    implementation 'com.google.firebase:firebase-analytics:21.0.0'
}
