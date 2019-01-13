# rso-storage

Projekt se builda sedaj z Java 11. Nastavi JAVA_HOME na ta JDK. Pravilnost nastavitve se preveri preko:

```
mvn -version
```

Tu mora pisati, da se uporablja Java 11. Če vmes spremeniš JAVA_HOME moraš zagnati shell (in IntelliJ, če uporabljaš
terminal v njem).


PORT: 8083

Kako ustvariti secret:
``
kubectl create secret generic storage-key --from-file=key.json=storage-key.json
``

To naredi secret imenovan "storage-key". File se preimenuje v key.json in se shrani v /var/secrets/google/key.json .

storage-key.json je service account key, ki ima določene pravice. Key se lahko zbriše po uspešni izvedbi zgornje komande.