package fr.btssio.komeet.api.etl.job;

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

    public static final String DELETE_TEMP_FILE_STEP = "deleteTempFileStep";
    public static final String SAVE_ROLE_TABLE_STEP = "saveRoleTableStep";
    public static final String SAVE_EQUIPMENT_TABLE_STEP = "saveEquipmentTableStep";
    public static final String SAVE_IMAGE_TABLE_STEP = "saveImageTableStep";
    public static final String SAVE_COMPANY_TABLE_STEP = "saveCompanyTableStep";
    public static final String SAVE_ROOM_TABLE_STEP = "saveRoomTableStep";
    public static final String SAVE_USER_TABLE_STEP = "saveUserTableStep";
    public static final String SAVE_EQUIP_TABLE_STEP = "saveEquipTableStep";
    public static final String SAVE_FAVORITE_TABLE_STEP = "saveFavoriteTableStep";
    public static final String WRITE_RESTORE_FILE_STEP = "writeRestoreFileStep";
    public static final String ZIP_AND_SAVE_STEP = "zipAndSaveStep";
    public static final String DELETE_OLD_ZIP_STEP = "deleteOldZipStep";
    public static final String PURGE_JOB_TABLE_STEP = "purgeJobTableStep";

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
            @Qualifier(DELETE_OLD_ZIP_STEP) Step deleteOldZipStep,
            @Qualifier(PURGE_JOB_TABLE_STEP) Step purgeJobTableStep
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
                .next(purgeJobTableStep)
                .build();
    }
}
