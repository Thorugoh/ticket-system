class AvailableState: SeatState {
    override fun reserve(seat: Seat) {
        seat.state = ReservedState()
        println("Seat ${seat.number} reserved")
    }

    override fun purchase(seat: Seat) {
        seat.state = SoldState()
        println("Seat ${seat.number} sold")
    }

    override fun cancel(seat: Seat) {
        println("Cannot cancel. Seat ${seat.number} is available")
    }
}