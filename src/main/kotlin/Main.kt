import java.time.LocalDateTime
import java.util.*


data class Show(val id: String, val name: String, val date: LocalDateTime, val venue: Venue)

data class Venue(val id: String, val name: String, val zones: List<Zone>)

data class Zone(val id: String, val name: String, val capacity: Int, val seats: MutableList<Seat>)

data class Seat(val id: String, val number: String, var state: SeatState)

data class Ticket(val id: String, val show: Show, val seat: Seat)

class TicketSystem {
    private val shows = mutableListOf<Show>()

    fun addShow(show: Show) {
        shows.add(show)
    }

    fun sellTicket(showId: String, zoneId: String, seatNumber: String): Ticket? {
        val show = shows.find { it.id == showId } ?: return null
        val seat = show.venue.zones.find { it.id == zoneId }?.seats?.find { it.number == seatNumber && it.state is AvailableState } ?: return null

        if (show.venue.isOverCapacity()) {
            println("Cannot sell ticket. Venue is over capacity.")
            return null
        }

        seat.state.purchase(seat)
        return Ticket(UUID.randomUUID().toString(), show, seat)
    }

    private fun Venue.isOverCapacity(): Boolean {
        val totalSeats = this.zones.sumOf { it.seats.size }
        val occupiedSeats = this.zones.sumOf { zone -> zone.seats.count { it.state is SoldState } }
        return occupiedSeats >= totalSeats;
    }
}

fun main() {
    val zoneASeats = (1..50).map { Seat("A$it", "A$it", AvailableState()) }.toMutableList()
    val zoneBSeats = (1..50).map { Seat("B$it", "B$it", AvailableState()) }.toMutableList()
    val zoneA = Zone("zoneA", "Zone A", 50, zoneASeats)
    val zoneB = Zone("zoneB", "Zone B", 50, zoneBSeats)
    val venue = Venue("venue1", "Some Venue", listOf(zoneA, zoneB))

    val show = Show("show1", "Some Show", LocalDateTime.now(), venue)

    val ticketSystem = TicketSystem()
    ticketSystem.addShow(show)

    val ticket = ticketSystem.sellTicket("show1", "zoneA", "A1")
    ticket?.let {
        println("Ticket sold for seat ${it.seat.number} at show ${it.show.name}")
    } ?: println("Ticket sale failed.")

    val secondTicketAttempt = ticketSystem.sellTicket("show1", "zoneA", "A1")
    secondTicketAttempt?.let {
        println("Ticket sold for seat ${it.seat.number} at show ${it.show.name}")
    } ?: println("Ticket sale failed. Seat may already be booked or sold.")
}