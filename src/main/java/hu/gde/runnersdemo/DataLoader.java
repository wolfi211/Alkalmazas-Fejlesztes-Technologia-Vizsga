package hu.gde.runnersdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    private final RunnerRepository runnerRepository;
    private final SponsorRepository sponsorRepository;

    @Autowired
    public DataLoader(RunnerRepository runnerRepository, SponsorRepository sponsorRepository) {
        this.runnerRepository = runnerRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public void run(String... args) {
        SponsorEntity sponsorEntity1 = new SponsorEntity();
        sponsorEntity1.setName("Adidas");

        SponsorEntity sponsorEntity2 = new SponsorEntity();
        sponsorEntity2.setName("Puma");

        sponsorRepository.save(sponsorEntity1);
        sponsorRepository.save(sponsorEntity2);

        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setRunnerName("Tomi");
        runnerEntity.setAveragePace(310);
        runnerEntity.setHeight(180);
        runnerEntity.setSponsor(sponsorEntity1);

        LapTimeEntity laptime1 = new LapTimeEntity();
        laptime1.setLapNumber(1);
        laptime1.setTimeSeconds(120);
        laptime1.setRunner(runnerEntity);

        LapTimeEntity laptime2 = new LapTimeEntity();
        laptime2.setLapNumber(2);
        laptime2.setTimeSeconds(110);
        laptime2.setRunner(runnerEntity);

        runnerEntity.getLaptimes().add(laptime1);
        runnerEntity.getLaptimes().add(laptime2);

        runnerRepository.save(runnerEntity);

        RunnerEntity runnerEntity2 = new RunnerEntity();
        runnerEntity2.setRunnerName("Zsuzsi");
        runnerEntity2.setAveragePace(290);
        runnerEntity2.setHeight(168);
        runnerEntity2.setSponsor(sponsorEntity2);

        LapTimeEntity laptime3 = new LapTimeEntity();
        laptime3.setLapNumber(1);
        laptime3.setTimeSeconds(95);
        laptime3.setRunner(runnerEntity2);

        LapTimeEntity laptime4 = new LapTimeEntity();
        laptime4.setLapNumber(2);
        laptime4.setTimeSeconds(100);
        laptime4.setRunner(runnerEntity2);

        runnerEntity2.getLaptimes().add(laptime3);
        runnerEntity2.getLaptimes().add(laptime4);

        runnerRepository.save(runnerEntity2);

        RunnerEntity runnerEntity3 = new RunnerEntity();
        runnerEntity3.setRunnerName("Richárd");
        runnerEntity3.setAveragePace(300);
        runnerEntity3.setHeight(175);
        runnerEntity3.setSponsor(sponsorEntity1);

        LapTimeEntity laptime5 = new LapTimeEntity();
        laptime5.setLapNumber(1);
        laptime5.setTimeSeconds(100);
        laptime5.setRunner(runnerEntity3);

        LapTimeEntity laptime6 = new LapTimeEntity();
        laptime6.setLapNumber(2);
        laptime6.setTimeSeconds(110);
        laptime6.setRunner(runnerEntity3);

        runnerEntity3.getLaptimes().add(laptime5);
        runnerEntity3.getLaptimes().add(laptime6);

        runnerRepository.save(runnerEntity3);
    }
}

