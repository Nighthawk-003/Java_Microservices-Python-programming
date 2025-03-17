import requests
import sqlite3
import string
import time
from bs4 import BeautifulSoup
from collections import Counter

# Constants
DB_NAME = "scraped_data.db"
URL = "https://en.wikipedia.org/wiki/India"
IGNORE_WORDS = {'the', 'on', 'if', 'as', 'for', 'and', 'at', 'that', 'is', 'a', 'to', 'of', 'in', 'it', 'with', 'this'}

def setup_database():
    """Creates an SQLite database and a table to store word counts."""
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("DROP TABLE IF EXISTS word_count")
        cursor.execute("""
            CREATE TABLE word_count (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                word TEXT UNIQUE,
                count INTEGER
            )
        """)
        conn.commit()
        print("Database setup complete.")

def scrape_wikipedia():
    """Scrapes the Wikipedia page and returns the extracted text."""
    try:
        print(f"Fetching data from: {URL} ...")
        response = requests.get(URL, headers={"User-Agent": "Mozilla/5.0"})
        response.raise_for_status()
        print("Page loaded successfully!")

        soup = BeautifulSoup(response.text, "html.parser")
        paragraphs = soup.find_all("p")
        text = " ".join(p.get_text(strip=True) for p in paragraphs)

        return text.lower()  # Convert text to lowercase
    except requests.exceptions.RequestException as e:
        print(f"Request error: {e}")
        return ""

def process_text(text):
    """Processes the text, removes ignored words, and returns a word frequency dictionary."""
    # Remove punctuation
    translator = str.maketrans("", "", string.punctuation)
    words = text.translate(translator).split()

    # Remove ignored words
    filtered_words = [word for word in words if word not in IGNORE_WORDS]

    # Count word occurrences
    word_counts = Counter(filtered_words)
    
    return word_counts

def store_in_database(word_counts):
    """Stores the word count data in the SQLite database."""
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        data = [(word, count) for word, count in word_counts.items()]
        cursor.executemany("INSERT INTO word_count (word, count) VALUES (?, ?)", data)
        conn.commit()
        print(f"{len(data)} words saved to the database!")

def preview_database(limit=10):
    """Displays a preview of the stored word frequency data."""
    print("\n--------------- Previewing saved words: ----------------")
    with sqlite3.connect(DB_NAME) as conn:
        cursor = conn.cursor()
        cursor.execute("SELECT word, count FROM word_count ORDER BY count DESC LIMIT ?", (limit,))
        rows = cursor.fetchall()
        for word, count in rows:
            print(f"{word}: {count}")

if __name__ == "__main__":
    start_time = time.time()

    setup_database()
    text_data = scrape_wikipedia()
    
    if text_data:
        word_frequencies = process_text(text_data)
        store_in_database(word_frequencies)
        preview_database()

    end_time = time.time()
    print(f"\nExecution Time: {end_time - start_time:.2f} seconds")
