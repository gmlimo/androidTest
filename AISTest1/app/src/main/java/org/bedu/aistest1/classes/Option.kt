package org.bedu.aistest1.classes

class Option (
    private var imageResource: Int,
    private var option: String
    ) {

    //Setters
    fun setImage(ID: Int) {
        this.imageResource = ID
    }

    fun setText(option: String){
        this.option = option
    }

    //Getters
    fun getImage() = this.imageResource
    fun getOption() = this.option
}