package ir.ah.igapweather.other.util
interface Mapper<R, D> {
    fun mapFrom(type: R): D
}