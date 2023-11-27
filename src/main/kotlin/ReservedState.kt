class ReservedState : SeatState {
    override fun reserve(seat: Seat) {
        println("Seat ${seat.number} is already reserved.")
    }

    override fun purchase(seat: Seat) {
        seat.state = SoldState()
        println("Seat ${seat.number} sold.")
    }

    override fun cancel(seat: Seat) {
        seat.state = AvailableState()
        println("Reservation of seat ${seat.number} cancelled.")
    }
}