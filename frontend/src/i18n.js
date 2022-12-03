import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import Backend from 'i18next-http-backend';
import { initReactI18next } from 'react-i18next';

i18n
	.use(Backend)
	.use(LanguageDetector)
	.use(initReactI18next) // bind react-i18next to the instance
	.init({
		defaultLanguage: 'en-GB',
		fallbackLng: 'en-GB',
		debug: process.env.NODE_ENV === 'development',
		react: {
			useSuspense: true,
		},
	});

export default i18n;
