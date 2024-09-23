/** @type {import("eslint").Linter.Config} */
module.exports = {
    extends: [
      'plugin:astro/recommended',
      'plugin:react/recommended',
      'plugin:tailwindcss/recommended'
    ],
    plugins: ['react', 'tailwindcss'],
    parserOptions: {
      sourceType: 'module',
      ecmaVersion: 'latest',
      ecmaFeatures: {
        jsx: true
      }
    },
    settings: {
      react: {
        version: 'detect'
      }
    },
    overrides: [
      {
        files: ['*.astro'],
        parser: 'astro-eslint-parser',
        parserOptions: {
          extraFileExtensions: ['.astro']
        },
        rules: {
          // Reglas específicas para archivos .astro
          'astro/no-set-html-directive': 'error',
          'tailwindcss/no-custom-classname': 'warn'
        }
      },
      {
        files: ['*.js', '*.jsx'],
        rules: {
          // Reglas específicas para archivos .js y .jsx
          'react/prop-types': 'off' // Desactiva la validación de prop-types si no las estás usando
        }
      }
    ],
    rules: {
      // Reglas globales
      'tailwindcss/classnames-order': 'warn',
      'tailwindcss/no-contradicting-classname': 'error'
    }
  }