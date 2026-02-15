# Randy

Randy is a desktop task manager app that keeps track of your todos, deadlines, and events.
It works through a chat interface -- just type a command and Randy responds. Randy goes by the
persona **Vikkstar** and will refer to you as **Deji** (yes it's a bit extra but that's the vibe).

![Product Screenshot](Ui.png)

## Quick Start

1. Make sure you have **Java 17** installed.
2. Download `randy.jar` from the [releases page](https://github.com/randalliasdanx/ip/releases).
3. Copy it to a folder you want to use.
4. Open a terminal, `cd` into that folder, and run:
   ```
   java -jar randy.jar
   ```
5. The chat window should pop up. Type a command and press Enter or click Send.

## Command Format

- Words in `UPPER_CASE` are parameters you fill in yourself.
  e.g. `todo DESCRIPTION` means you type something like `todo buy groceries`.
- Dates should be in `yyyy-MM-dd` format (e.g. `2025-03-15`). If you type an invalid
  date like `2025-02-30`, it won't be recognised as a date.
- Commands are **not** case-sensitive. `TODO`, `Todo`, and `todo` all work.

## Features

### Adding a todo: `todo`

Adds a task with just a description (no date).

Format: `todo DESCRIPTION`

Example: `todo read chapter 5`

Expected output:
```
LESGOOO! added that one Deji:
[T][ ] read chapter 5
you now have 1 tasks in the bag!
```

### Adding a deadline: `deadline`

Adds a task with a due date. If the date is valid, Randy formats it nicely.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline submit essay /by 2025-03-20`

Expected output:
```
LESGOOO! added that one Deji:
[D][ ] submit essay (by: Mar 20 2025)
you now have 2 tasks in the bag!
```

### Adding an event: `event`

Adds a task that spans a date range.

Format: `event DESCRIPTION /from START /to END`

Example: `event hackathon /from 2025-04-01 /to 2025-04-03`

Expected output:
```
LESGOOO! added that one Deji:
[E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
you now have 3 tasks in the bag!
```

> **Note:** If the start date is after the end date, Randy will reject the command.

### Listing all tasks: `list`

Shows everything you currently have.

Format: `list`

Expected output:
```
here's the lineup Deji:
1. [T][ ] read chapter 5
2. [D][ ] submit essay (by: Mar 20 2025)
3. [E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
```

### Marking a task as done: `mark`

Marks a task as completed. The `[ ]` becomes `[X]`.

Format: `mark INDEX`

Example: `mark 1`

Expected output:
```
BIG W Deji! smashed it:
[T][X] read chapter 5
```

### Unmarking a task: `unmark`

Sets a task back to not done.

Format: `unmark INDEX`

Example: `unmark 1`

Expected output:
```
alright Deji, unmarked this one:
[T][ ] read chapter 5
```

### Deleting a task: `delete`

Removes a task from the list entirely.

Format: `delete INDEX`

Example: `delete 2`

Expected output:
```
gone and dusted Deji:
[D][ ] submit essay (by: Mar 20 2025)
you now have 2 tasks
```

### Finding tasks by keyword: `find`

Searches your tasks for a keyword. Case-insensitive.

Format: `find KEYWORD`

Example: `find chapter`

Expected output:
```
found these for ya Deji:
1. [T][ ] read chapter 5
```

### Filtering tasks by date: `on`

Shows tasks that fall on a specific date. For deadlines, it checks the due date.
For events, it checks if the date is within the start-to-end range.

Format: `on DATE`

Example: `on 2025-04-02`

Expected output:
```
tasks on 2025-04-02 Deji:
1. [E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
```

### Exiting: `bye`

Closes the app.

Format: `bye`

## Error Handling

Randy handles bad input gracefully instead of crashing. Some things it catches:

- **Unknown commands** -- tells you it doesn't recognise the command.
- **Missing details** -- reminds you to include a description or date.
- **Wrong format** -- shows the expected format for deadline/event commands.
- **Invalid task number** -- tells you if you typed a non-number or out-of-range index.
- **Duplicate tasks** -- warns you if an identical task already exists.
- **Bad dates** -- rejects dates like Feb 30 that don't actually exist.
- **Start after end** -- rejects events where the start date comes after the end date.

Errors show up in red in the GUI so they're easy to spot.

## Data Storage

Your tasks get saved automatically to `./data/storage.txt` after every command.
If this file is missing when you open Randy, it just starts with an empty list.
If a line in the file is corrupted, Randy skips that line and loads the rest.

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| todo | `todo DESCRIPTION` | `todo wash dishes` |
| deadline | `deadline DESCRIPTION /by DATE` | `deadline report /by 2025-03-20` |
| event | `event DESCRIPTION /from START /to END` | `event camp /from 2025-06-01 /to 2025-06-03` |
| list | `list` | `list` |
| mark | `mark INDEX` | `mark 2` |
| unmark | `unmark INDEX` | `unmark 2` |
| delete | `delete INDEX` | `delete 3` |
| find | `find KEYWORD` | `find book` |
| on | `on DATE` | `on 2025-03-20` |
| bye | `bye` | `bye` |
