package io.VideoClub.Model;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reservation {
    public enum StatusReserve {
        ACTIVE, //ini on, finished off
        FINISHED, //ini on finised on == end
        PENDING  //ini on , finished off and end past
    }
    public Product pro;
    public IClient cli;
    public LocalDate ini;
    public LocalDate end;
    public LocalDate finished;
    public StatusReserve status;

    private Reservation() {
    }

    ;
    public Reservation(Product pro, IClient cli) {
        this.pro = pro;
        this.cli = cli;
        this.finished = null;
        this.pro.setStatus(Product.Status.RESERVED);
        ini = LocalDate.now();
        end = LocalDate.now().plusDays(2);
        if (end.getDayOfWeek() == DayOfWeek.SUNDAY) {
            end = end.plusDays(1);
        }
        status = StatusReserve.ACTIVE;
    }

    public Reservation(Product pro, IClient cli, LocalDate ini, LocalDate end, LocalDate finished, StatusReserve status) {

        if (finished == null && end.isBefore(LocalDate.now())) {
            this.status = StatusReserve.PENDING;
        } else {
            this.status = status;
        }
        this.pro = pro;
        this.cli = cli;
        this.ini = ini;
        this.end = end;
        this.finished = finished;

    }

    public boolean equals(Object o) {
        boolean result = false;
        if (o != null) {
            if (o instanceof Reservation) {
                Reservation other = (Reservation) o;
                if (this.pro.equals(other.pro)
                        && this.cli.equals(other.cli)
                        && this.ini.equals(other.ini)
                        && this.end.equals(other.end)
                        && this.status == other.status) {
                    result = true;
                }
            }
        }
        return result;
    }

    public void addDays(int days) {
        this.end = LocalDate.now().plusDays(days);
        this.status = StatusReserve.ACTIVE;
    }

    public void finish() {
        this.finished = LocalDate.now();
        this.pro.setStatus(Product.Status.AVAILABLE);
        this.status = StatusReserve.FINISHED;
    }

    public double getIncome() {
        double income = 0;

        switch (this.status) {
            case ACTIVE:
                income = (this.pro.getPrize() / 2) * (float) this.ini.until(end).getDays();
                break;
            case PENDING:
                income = (this.pro.getPrize() / 2) * (float) this.ini.until(LocalDate.now()).getDays();
                break;
            case FINISHED:
                income = this.ini.until(finished).getDays() == 0 ? this.pro.getPrize() : ((this.pro.getPrize() / 2) * (float) this.ini.until(finished).getDays());
        }

        return income;
    }

    public int compareTo(Reservation o) {
        if (this.equals(o)) {
            return 0;
        } else {
            return this.cli.getID().compareTo(o.cli.getID()) + this.pro.compareTo(o.pro) + this.ini.compareTo(o.ini);
        }
    }

    @Override
    public String toString() {
        return "Reservation{" + "pro=" + pro + ", cli=" + cli + ", ini=" + ini + ", end=" + end + ", finished=" + finished + ", status=" + status + '}';
    }

  
}
