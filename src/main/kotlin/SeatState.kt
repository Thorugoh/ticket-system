
interface SeatState {
    fun reserve(seat: Seat)
    fun purchase(seat: Seat)
    fun cancel(seat: Seat)
}
