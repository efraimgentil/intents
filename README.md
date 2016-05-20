# intents

Projeto demonstrando uso de algumas Intents nativas do Android
https://github.com/efraimgentil/intents/blob/master/app/src/main/java/intents/efraimgentil/me/intents/MainActivity.java

```java
  //Envia para o gerenciador de contatos as informações para adicionar um novo contato
  Intent intent = new Intent(Intent.ACTION_INSERT);
  intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
  intent.putExtra(ContactsContract.Intents.Insert.NAME, "Efraim Gentil");
  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "efraim.gentil@gmail.com");
  intent.putExtra(ContactsContract.Intents.Insert.PHONE, "987274131");
  if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(intent);
  }
```

```java
   //Solicita ao gerenciador de contatos as informações de um contato 
  Intent intent = new Intent(Intent.ACTION_PICK);
  intent.setType( ContactsContract.Contacts.CONTENT_TYPE );
  if (intent.resolveActivity(getPackageManager()) != null) {
    startActivityForResult(intent, REQUEST_SELECT_CONTACT);
  }
  ....
```

```java
  //Solicita a abertura de uma pagina web no navegador padrao
  String url = "https://github.com/efraimgentil";
  Intent i = new Intent(Intent.ACTION_VIEW);
  i.setData(Uri.parse(url));
  startActivity(i);
```
