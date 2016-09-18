package com.weekmenu

class Item {

    Long id
    
    String name
    
    static constraints = {
        id generator: 'increment', name: 'id' 
    }
}
