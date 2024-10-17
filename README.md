# HowToThreads
L'essentiel des threads pour comprendre les phénomènes de concurrence

Avec la généralisation des processeurs multicoeurs dans les appareils tels que les ordinateurs, smartphones, tablettes 
et autres dispositifs avec système d'exploitation, développer une application multithreadée devient un enjeu majeur pour  
répondre aux besoins toujours croissants et nombreux des utilisateurs.

Lancement d'un thread: *programmez_thread*
- Etendre la classe Thread et surcharger la methode run() *MyThread*
- Implémener la méthode run() de Runnable, et associer à une instance de Thread.

Cette 2ème méthode est préférable. En effet, elle présente une meilleure conception orientée objet

Pile d'appel avec 2 threads : *StackMain*

Priorité des Threads : *ThreadPriority*
Il est important de noter qu'un Thread ayant la plus grande priorité ne se termine pas forcément en 1er.

Exemple de code thread-safe : *AccountDemo*

Synthèse des données de synchronisation : *SynchronizationSummary*