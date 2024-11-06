## Structure general du projet ##


- GameObject (classe de base)
   ├── Entity (pour monstres et obstacles)
   │   ├── Monster
   │   └── Obstacle
   └── Character (pour les personnages jouables)
       ├── Mage
       ├── Elf
       └── Warrior

- Weapon (classe de base)
   ├── Sword
   ├── Axe
   └── Bow

- Autres classes
   ├── Game (classe principale)
   ├── Dungeon (gestion de la carte)
   └── Shop (magasin d'armes)
