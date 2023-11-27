class SoldState : SeatState {
    override fun reserve(seat: Seat) {
        println("Seat ${seat.number} is already sold.")
    }

    override fun purchase(seat: Seat) {
        println("Seat ${seat.number} is already sold.")
    }

    override fun cancel(seat: Seat) {
        println("Cannot cancel. Seat ${seat.number} is sold.")
    }
}