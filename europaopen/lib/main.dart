import 'package:europaopen/app.dependencies.dart';
import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/blocs/info/info.cubit.dart';
import 'package:europaopen/data/cache.provider.dart';
import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/data/repositories/country.repository.dart';
import 'package:europaopen/data/repositories/domain.repository.dart';
import 'package:europaopen/data/repositories/region.repository.dart';
import 'package:europaopen/data/repositories/rule.repository.dart';
import 'package:europaopen/ui/app.dart';
import 'package:europaopen/utils/constants/api.const.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:hive_flutter/hive_flutter.dart';

Future<void> main() async {
  await Hive.initFlutter();
  Hive.registerAdapter<CountryModel>(CountryModelAdapter());
  Hive.registerAdapter<CountryDirection>(CountryDirectionAdapter());
  await Hive.openBox<CountryModel>(CacheProvider.countryBoxName);

  runApp(
    MultiRepositoryProvider(
      providers: [
        RepositoryProvider<ICountryRepository>.value(
          value: AppDependencies.countryRepositoryInstance(
            AppDependencies.apiProviderInstance(ApiConst.reopenEuropaURL),
            AppDependencies.countryCacheProviderInstance(),
          ),
        ),
        RepositoryProvider<IRegionRepository>.value(
          value: AppDependencies.regionRepositoryInstance(
            AppDependencies.apiProviderInstance(ApiConst.reopenEuropaURL),
          ),
        ),
        RepositoryProvider<IDomainRepository>.value(
          value: AppDependencies.domainRepositoryInstance(
            AppDependencies.apiProviderInstance(ApiConst.reopenEuropaURL),
          ),
        ),
        RepositoryProvider<IRuleRepository>.value(
          value: AppDependencies.ruleRepositoryInstance(
            AppDependencies.apiProviderInstance(ApiConst.reopenEuropaURL),
          ),
        ),
      ],
      child: MultiBlocProvider(
        providers: [
          BlocProvider<CountryCubit>(
            create: (BuildContext context) => CountryCubit(
              context.read<ICountryRepository>(),
            ),
          ),
          BlocProvider<InfoCubit>(
            create: (BuildContext context) => InfoCubit(
              context.read<IRegionRepository>(),
              context.read<IDomainRepository>(),
              context.read<IRuleRepository>(),
            ),
          ),
        ],
        child: const App(),
      ),
    ),
  );
}
