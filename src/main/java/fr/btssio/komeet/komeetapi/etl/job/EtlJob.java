package fr.btssio.komeet.komeetapi.etl.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtlJob {

    public static final String JOB_NAME = "komeetEtlJob";

    private static final String DELETE_TEMP_FILE_STEP = "deleteTempFileStep";
    private static final String SAVE_ROLE_TABLE_STEP = "saveRoleTableStep";
    private static final String SAVE_EQUIPMENT_TABLE_STEP = "saveEquipmentTableStep";
    private static final String SAVE_IMAGE_TABLE_STEP = "saveImageTableStep";
    private static final String SAVE_COMPANY_TABLE_STEP = "saveCompanyTableStep";
    private static final String SAVE_ROOM_TABLE_STEP = "saveRoomTableStep";
    private static final String SAVE_USER_TABLE_STEP = "saveUserTableStep";
    private static final String SAVE_EQUIP_TABLE_STEP = "saveEquipTableStep";
    private static final String SAVE_FAVORITE_TABLE_STEP = "saveFavoriteTableStep";
    private static final String WRITE_RESTORE_FILE_STEP = "writeRestoreFileStep";
    private static final String ZIP_AND_SAVE_STEP = "zipAndSaveStep";
    private static final String DELETE_OLD_ZIP_STEP = "deleteOldZipStep";

    private final JobBuilder jobBuilder;

    public EtlJob(JobBuilder jobBuilder) {
        this.jobBuilder = jobBuilder;
    }

    @Bean(name = JOB_NAME)
    public Job job(
            @Qualifier(DELETE_TEMP_FILE_STEP) Step deleteTempFileStep,
            @Qualifier(SAVE_ROLE_TABLE_STEP) Step saveRoleTableStep,
            @Qualifier(SAVE_EQUIPMENT_TABLE_STEP) Step saveEquipmentTableStep,
            @Qualifier(SAVE_IMAGE_TABLE_STEP) Step saveImageTableStep,
            @Qualifier(SAVE_COMPANY_TABLE_STEP) Step saveCompanyTableStep,
            @Qualifier(SAVE_ROOM_TABLE_STEP) Step saveRoomTableStep,
            @Qualifier(SAVE_USER_TABLE_STEP) Step saveUserTableStep,
            @Qualifier(SAVE_EQUIP_TABLE_STEP) Step saveEquipTableStep,
            @Qualifier(SAVE_FAVORITE_TABLE_STEP) Step saveFavoriteTableStep,
            @Qualifier(WRITE_RESTORE_FILE_STEP) Step writeRestoreFileStep,
            @Qualifier(ZIP_AND_SAVE_STEP) Step zipAndSaveStep,
            @Qualifier(DELETE_OLD_ZIP_STEP) Step deleteOldZipStep
    ) {
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(deleteTempFileStep)
                .next(saveRoleTableStep)
                .next(saveEquipmentTableStep)
                .next(saveImageTableStep)
                .next(saveCompanyTableStep)
                .next(saveRoomTableStep)
                .next(saveUserTableStep)
                .next(saveEquipTableStep)
                .next(saveFavoriteTableStep)
                .next(writeRestoreFileStep)
                .next(zipAndSaveStep)
                .next(deleteOldZipStep)
                .build();
    }
}
