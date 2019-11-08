package Model

class Item {

    var id: Int = 0
    var itemName: String? = null
    var itemQuantity: String? = null
    var itemDateCreated: String? = null

    constructor() {}

    constructor(id: Int, itemName: String, itemQuantity: String, itemDateCreated: String) {
        this.id = id
        this.itemName = itemName
        this.itemQuantity = itemQuantity
        this.itemDateCreated = itemDateCreated
    }
}
